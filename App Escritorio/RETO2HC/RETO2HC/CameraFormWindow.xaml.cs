using System.Windows;
using System.Xml.Linq;

namespace RETO2HC
{
    public partial class CameraFormWindow : Window
    {
        public Camera CameraData { get; private set; }

        public CameraFormWindow(Camera camera = null)
        {
            InitializeComponent();

            if (camera == null)
            {
                // Creación
                CameraData = new Camera { IsSource67 = true };
            }
            else
            {
                // Edición (Clonar)
                CameraData = new Camera
                {
                    CameraId = camera.CameraId,
                    Name = camera.Name,
                    Address = camera.Address,
                    Road = camera.Road,
                    Kilometer = camera.Kilometer,
                    Latitude = camera.Latitude,
                    Longitude = camera.Longitude,
                    Url = camera.Url,
                    IsSource67 = true,
                    SourceId = 67
                };
            }
            this.DataContext = CameraData;
        }

        private void Save_Click(object sender, RoutedEventArgs e)
        {
            // --- 1. VALIDACIÓN DEL NOMBRE ---
            if (string.IsNullOrWhiteSpace(TxtName.Text))
            {
                MessageBox.Show("El nombre es obligatorio.", "Falta información", MessageBoxButton.OK, MessageBoxImage.Warning);
                return; // Detenemos la función, la ventana NO se cierra
            }

            // --- 2. VALIDACIÓN DE COORDENADAS (PUNTOS Y COMAS) ---
            // Leemos el texto de las cajas y cambiamos cualquier coma por un punto
            string latTexto = TxtLatitude.Text.Replace(",", ".");
            string lonTexto = TxtLongitude.Text.Replace(",", ".");

            double latFinal;
            double lonFinal;

            // Intentamos convertir a número usando formato internacional (el punto es decimal)
            bool esLatitudValida = double.TryParse(latTexto, System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, out latFinal);
            bool esLongitudValida = double.TryParse(lonTexto, System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, out lonFinal);

            if (!esLatitudValida)
            {
                MessageBox.Show("La Latitud no es válida. Por favor, escribe un número (ej: 43.25).", "Error de Formato", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            if (!esLongitudValida)
            {
                MessageBox.Show("La Longitud no es válida. Por favor, escribe un número.", "Error de Formato", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            // --- 3. GUARDAR LOS DATOS EN EL OBJETO ---
            // Si llegamos aquí, todo es correcto. Pasamos los valores de las cajas al objeto CameraData.

            // Textos (leídos directamente de las cajas por su x:Name)
            CameraData.Name = TxtName.Text;
            CameraData.Address = TxtAddress.Text;
            CameraData.Road = TxtRoad.Text;
            CameraData.Kilometer = TxtKilometer.Text;
            CameraData.Url = TxtUrl.Text;

            // Números (los valores 'double' que validamos arriba)
            CameraData.Latitude = latFinal;
            CameraData.Longitude = lonFinal;

            // Valores fijos que solicitaste
            CameraData.IsSource67 = true;
            CameraData.SourceId = 67;

            // --- 4. CERRAR LA VENTANA ---
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