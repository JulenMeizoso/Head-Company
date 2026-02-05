using System;
using System.Windows;
using System.Globalization;

namespace RETO2HC
{
    public partial class IncidenceFormWindow : Window
    {
        public Incidence IncidenceData { get; private set; }

        public IncidenceFormWindow(Incidence incidence = null)
        {
            InitializeComponent();

            if (incidence == null)
            {
                // --- CREACIÓN ---
                IncidenceData = new Incidence
                {
                    IsSource67 = true,
                    // Inicializamos con fecha y hora actual
                    StartDate = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"),
                    SourceId = 67
                };

                // Ponemos fecha y hora actuales en los controles visuales
                DtPicker.SelectedDate = DateTime.Now;
                TxtTime.Text = DateTime.Now.ToString("HH:mm");
            }
            else
            {
                // --- EDICIÓN ---
                IncidenceData = new Incidence
                {
                    IncidenceId = incidence.IncidenceId,
                    Type = incidence.Type,
                    Level = incidence.Level,
                    Description = incidence.Description,
                    City = incidence.City,
                    Road = incidence.Road,
                    Latitude = incidence.Latitude,
                    Longitude = incidence.Longitude,
                    StartDate = incidence.StartDate,
                    IsSource67 = true,
                    SourceId = 67
                };

                // LÓGICA PARA CARGAR LA FECHA Y HORA VISUALMENTE
                if (DateTime.TryParse(incidence.StartDate, out DateTime fechaGuardada))
                {
                    DtPicker.SelectedDate = fechaGuardada; // Parte Fecha
                    TxtTime.Text = fechaGuardada.ToString("HH:mm"); // Parte Hora
                }
            }

            this.DataContext = IncidenceData;
        }

        private void Save_Click(object sender, RoutedEventArgs e)
        {
            // 1. Validar Ubicación
            if (string.IsNullOrWhiteSpace(TxtRoad.Text))
            {
                MessageBox.Show("Por favor inserte una ubicación o carretera.", "Falta información", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            // 2. Validar Coordenadas (Puntos y Comas)
            string latTexto = TxtLatitude.Text.Replace(",", ".");
            string lonTexto = TxtLongitude.Text.Replace(",", ".");

            if (!double.TryParse(latTexto, NumberStyles.Any, CultureInfo.InvariantCulture, out double latFinal))
            {
                MessageBox.Show("La Latitud no es válida.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            if (!double.TryParse(lonTexto, NumberStyles.Any, CultureInfo.InvariantCulture, out double lonFinal))
            {
                MessageBox.Show("La Longitud no es válida.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            // 3. VALIDAR Y COMBINAR FECHA + HORA
            if (DtPicker.SelectedDate == null)
            {
                MessageBox.Show("Debes seleccionar una fecha.", "Error", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            // Comprobar formato de hora (HH:mm)
            string horaStr = TxtTime.Text.Trim();
            // Intentamos parsear solo la hora para ver si es válida (ej: 14:30)
            if (!DateTime.TryParseExact(horaStr, "HH:mm", CultureInfo.InvariantCulture, DateTimeStyles.None, out DateTime soloHora))
            {
                // Si falla, intentamos con H:mm por si puso 9:30 en vez de 09:30
                if (!DateTime.TryParseExact(horaStr, "H:mm", CultureInfo.InvariantCulture, DateTimeStyles.None, out soloHora))
                {
                    MessageBox.Show("El formato de la hora es incorrecto. Usa HH:mm (ej: 14:30).", "Error", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }
            }

            // Combinar Fecha seleccionada + Hora escrita
            DateTime fechaSeleccionada = DtPicker.SelectedDate.Value;

            // Creamos un nuevo DateTime con la fecha del Picker y la hora del TextBox
            DateTime fechaFinalCombinada = new DateTime(
                fechaSeleccionada.Year,
                fechaSeleccionada.Month,
                fechaSeleccionada.Day,
                soloHora.Hour,
                soloHora.Minute,
                0); 

            // 4. GUARDAR DATOS EN EL OBJETO
            IncidenceData.Road = TxtRoad.Text;
            IncidenceData.Latitude = latFinal;
            IncidenceData.Longitude = lonFinal;

            // Guardamos la fecha combinada en el formato que pide tu base de datos
            IncidenceData.StartDate = fechaFinalCombinada.ToString("yyyy-MM-dd HH:mm:ss");

            DialogResult = true;
            Close();
        }

        private void Cancel_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = false;
            Close();
        }
    }
}