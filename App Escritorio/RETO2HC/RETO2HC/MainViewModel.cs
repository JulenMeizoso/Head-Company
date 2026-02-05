using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Globalization;
using System.Linq;
using System.Net.Http;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Data;
using System.Windows.Input;
using System.Windows.Media.Imaging;

namespace RETO2HC

{

    // --- 1. MODELOS ---

    public class User

    {
        public int UserId { get; set; }
        [JsonProperty("mail")] public string Email { get; set; }
        [JsonProperty("contra")] public string Password { get; set; }
    }

    public class Camera

    {
        [JsonProperty("cameraId")] public int CameraId { get; set; }
        [JsonProperty("sourceId")] public int SourceId { get; set; } = 67;
        [JsonProperty("cameraName")] public string Name { get; set; }
        [JsonProperty("address")] public string Address { get; set; }
        [JsonProperty("road")] public string Road { get; set; }
        [JsonProperty("kilometer")] public string Kilometer { get; set; }
        [JsonProperty("latitude")] public double Latitude { get; set; }
        [JsonProperty("longitude")] public double Longitude { get; set; }
        [JsonProperty("urlImage")] public string Url { get; set; }

        private bool _isSource67;

        public bool IsSource67

        {

            get { return SourceId == 67 || _isSource67; }
            set { _isSource67 = value; }

        }
    }

    public class Incidence


    {

        [JsonProperty("incidenceId")] public int IncidenceId { get; set; }
        [JsonProperty("sourceId")] public int SourceId { get; set; } = 67;
        [JsonProperty("incidenceType")] public string Type { get; set; }
        [JsonProperty("incidenceLevel")] public string Level { get; set; }
        [JsonProperty("incidenceName")] public string Name { get; set; }
        [JsonProperty("incidenceDescription")] public string Description { get; set; }
        [JsonProperty("cityTown")] public string City { get; set; }
        [JsonProperty("road")] public string Road { get; set; }
        [JsonProperty("latitude")] public double Latitude { get; set; }
        [JsonProperty("longitude")] public double Longitude { get; set; }
        [JsonProperty("startDate")] public string StartDate { get; set; }

        public string DisplayDate => !string.IsNullOrEmpty(StartDate) ? StartDate : "-";

        private bool _isSource67;

        public bool IsSource67



        {



            get { return SourceId == 67 || _isSource67; }



            set { _isSource67 = value; }



        }



    }







    public class ApiResponse<T>



    {



        [JsonProperty("items")] public List<T> Items { get; set; }



    }







    // --- 2. CONVERTIDOR ---



    public class BoolToVisConverter : IValueConverter



    {



        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)



        {



            if (value is bool b && b) return Visibility.Visible;



            return Visibility.Collapsed;



        }



        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture) => throw new NotImplementedException();



    }







    // --- 3. VIEWMODELS ---







    public class MainViewModel : INotifyPropertyChanged



    {



        private object _currentView;



        public object CurrentView



        {



            get => _currentView;



            set { _currentView = value; OnPropertyChanged(); }



        }







        public ICommand ShowUsersCommand { get; }



        public ICommand ShowCamerasCommand { get; }



        public ICommand ShowIncidencesCommand { get; }



        public ICommand LogoutCommand { get; }



        public ICommand ExportPdfCommand { get; }



        public ICommand RefreshServerCommand { get; }







        public UsersViewModel UsersVM { get; set; }



        public CamerasViewModel CamerasVM { get; set; }



        public IncidenceViewModel IncidenceVM { get; set; }







        public ICommand NavigateUsersCommand => ShowUsersCommand;



        public ICommand NavigateCamerasCommand => ShowCamerasCommand;



        public ICommand NavigateIncidenceCommand => ShowIncidencesCommand;







        public MainViewModel()



        {



            UsersVM = new UsersViewModel();



            CamerasVM = new CamerasViewModel();



            IncidenceVM = new IncidenceViewModel();







            // Navegación restaurada a tu lógica original



            ShowUsersCommand = new RelayCommand(o => CurrentView = UsersVM);



            ShowCamerasCommand = new RelayCommand(o => CurrentView = CamerasVM);



            ShowIncidencesCommand = new RelayCommand(o => CurrentView = IncidenceVM);







            LogoutCommand = new RelayCommand(o => Application.Current.Shutdown());



            // Cambia la línea original por esta:

            ExportPdfCommand = new RelayCommand(async o => await ExportToPdf());



            RefreshServerCommand = new RelayCommand(async (o) => await RefreshServer());







            CurrentView = UsersVM;



            _ = LoadAllData();



        }







        private async Task LoadAllData()



        {



            await UsersVM.LoadData();



            await CamerasVM.LoadData();



            await IncidenceVM.LoadData();



        }



        private async Task ExportToPdf()

        {

            try

            {

                var listaIncidencias = IncidenceVM.AllIncidences ?? new List<Incidence>();



                if (listaIncidencias.Count == 0)

                {

                    System.Windows.MessageBox.Show("No hay incidencias cargadas para exportar.");

                    return;

                }



                var saveFileDialog = new Microsoft.Win32.SaveFileDialog

                {

                    FileName = $"Reporte_Incidencias_{DateTime.Now:yyyyMMdd}",

                    DefaultExt = ".pdf",

                    Filter = "Archivos PDF (.pdf)|*.pdf"

                };



                if (saveFileDialog.ShowDialog() == true)

                {

                    await Task.Run(() =>

                    {

                        using (var writer = new iText.Kernel.Pdf.PdfWriter(saveFileDialog.FileName))

                        using (var pdf = new iText.Kernel.Pdf.PdfDocument(writer))

                        {

                            var document = new iText.Layout.Document(pdf, iText.Kernel.Geom.PageSize.A4);

                            var fontBold = iText.Kernel.Font.PdfFontFactory.CreateFont(iText.IO.Font.Constants.StandardFonts.HELVETICA_BOLD);

                            var fontItalic = iText.Kernel.Font.PdfFontFactory.CreateFont(iText.IO.Font.Constants.StandardFonts.HELVETICA_OBLIQUE);



                            // --- CABECERA ---

                            document.Add(new iText.Layout.Element.Paragraph("REPORTE ESTRATÉGICO DE INCIDENCIAS")

                                .SetFont(fontBold).SetFontSize(20).SetFontColor(iText.Kernel.Colors.ColorConstants.BLUE));



                            document.Add(new iText.Layout.Element.Paragraph($"Generado el: {DateTime.Now:dd/MM/yyyy HH:mm}")

                                .SetFontSize(9).SetFont(fontItalic).SetTextAlignment(iText.Layout.Properties.TextAlignment.RIGHT));



                            // --- SECCIÓN 1: INCIDENCIAS POR TIPO ---

                            document.Add(new iText.Layout.Element.Paragraph("\n1. Análisis por Tipo de Incidencia").SetFont(fontBold).SetFontSize(14).SetUnderline());

                            byte[] chartTipo = CreateIncidenceChart(listaIncidencias);

                            document.Add(new iText.Layout.Element.Image(iText.IO.Image.ImageDataFactory.Create(chartTipo)).SetWidth(480).SetHorizontalAlignment(iText.Layout.Properties.HorizontalAlignment.CENTER));



                            // --- SECCIÓN 2: INCIDENCIAS POR FECHA (Mismo formato) ---

                            document.Add(new iText.Layout.Element.AreaBreak());

                            document.Add(new iText.Layout.Element.Paragraph("2. Análisis por Fecha de Registro").SetFont(fontBold).SetFontSize(14).SetUnderline());



                            byte[] chartFecha = CreateIncidenceDateChart(listaIncidencias);

                            if (chartFecha != null)

                            {

                                document.Add(new iText.Layout.Element.Image(iText.IO.Image.ImageDataFactory.Create(chartFecha)).SetWidth(480).SetHorizontalAlignment(iText.Layout.Properties.HorizontalAlignment.CENTER));

                            }



                            document.Add(new iText.Layout.Element.Paragraph($"\nTotal global de registros analizados: {listaIncidencias.Count}")

                                .SetFont(fontBold).SetFontSize(12).SetTextAlignment(iText.Layout.Properties.TextAlignment.CENTER));



                            document.Close();

                        }

                    });

                    System.Windows.MessageBox.Show("¡PDF de Incidencias generado con éxito!");

                }

            }

            catch (Exception ex) { System.Windows.MessageBox.Show("Error al generar el PDF: " + ex.Message); }

        }



        private byte[] CreateIncidenceChart(List<Incidence> incidencias)

        {

            var data = incidencias.GroupBy(i => i.Type ?? "Otras")

                .Select(g => new { Label = g.Key, Count = (double)g.Count() }).ToList();



            double total = data.Sum(x => x.Count);

            System.Windows.Media.DrawingVisual visual = new System.Windows.Media.DrawingVisual();

            double pixelsPerDip = System.Windows.Media.VisualTreeHelper.GetDpi(visual).PixelsPerDip;



            using (System.Windows.Media.DrawingContext dc = visual.RenderOpen())

            {

                dc.DrawRectangle(System.Windows.Media.Brushes.White, null, new System.Windows.Rect(0, 0, 1000, 600));

                double currentAngle = 0;

                double centerX = 250, centerY = 300, radius = 180;



                var brushes = new List<System.Windows.Media.Brush> { System.Windows.Media.Brushes.IndianRed, System.Windows.Media.Brushes.Orange, System.Windows.Media.Brushes.SteelBlue, System.Windows.Media.Brushes.MediumSeaGreen, System.Windows.Media.Brushes.SlateBlue };



                for (int i = 0; i < data.Count; i++)

                {

                    double sliceAngle = (data[i].Count / total) * 360;

                    var figure = new System.Windows.Media.PathFigure { StartPoint = new System.Windows.Point(centerX, centerY), IsClosed = true };

                    double startRad = currentAngle * Math.PI / 180;

                    double endRad = (currentAngle + sliceAngle) * Math.PI / 180;



                    figure.Segments.Add(new System.Windows.Media.LineSegment(new System.Windows.Point(centerX + Math.Cos(startRad) * radius, centerY + Math.Sin(startRad) * radius), true));

                    figure.Segments.Add(new System.Windows.Media.ArcSegment(new System.Windows.Point(centerX + Math.Cos(endRad) * radius, centerY + Math.Sin(endRad) * radius), new System.Windows.Size(radius, radius), sliceAngle, sliceAngle > 180, System.Windows.Media.SweepDirection.Clockwise, true));

                    dc.DrawGeometry(brushes[i % brushes.Count], new System.Windows.Media.Pen(System.Windows.Media.Brushes.White, 0.5), new System.Windows.Media.PathGeometry(new[] { figure }));



                    double ly = 100 + (i * 40);

                    dc.DrawRectangle(brushes[i % brushes.Count], null, new System.Windows.Rect(550, ly, 20, 20));

                    var text = new System.Windows.Media.FormattedText($"{data[i].Label}: {data[i].Count} ({(data[i].Count / total * 100):F1}%)",

                        System.Globalization.CultureInfo.InvariantCulture, System.Windows.FlowDirection.LeftToRight,

                        new System.Windows.Media.Typeface("Verdana"), 16, System.Windows.Media.Brushes.Black, pixelsPerDip);

                    dc.DrawText(text, new System.Windows.Point(585, ly - 2));

                    currentAngle += sliceAngle;

                }

            }

            var bmp = new RenderTargetBitmap(1000, 600, 96, 96, System.Windows.Media.PixelFormats.Pbgra32);

            bmp.Render(visual);

            var encoder = new PngBitmapEncoder();

            encoder.Frames.Add(BitmapFrame.Create(bmp));

            using (var ms = new System.IO.MemoryStream()) { encoder.Save(ms); return ms.ToArray(); }

        }



        private byte[] CreateIncidenceDateChart(List<Incidence> incidencias)

        {

            // Agrupamos y convertimos el string a una fecha válida para el reporte

            var data = incidencias

                .Where(i => !string.IsNullOrEmpty(i.StartDate))

                .Select(i =>
                {

                    DateTime.TryParse(i.StartDate, out DateTime dt);

                    return dt;

                })

                .Where(dt => dt != DateTime.MinValue)

                .GroupBy(dt => dt.ToString("dd/MM/yyyy"))

                .Select(g => new { Label = g.Key, Count = (double)g.Count() })

                .OrderByDescending(x => x.Label)

                .Take(8)

                .ToList();



            double total = data.Sum(x => x.Count);

            if (total == 0) return null;



            System.Windows.Media.DrawingVisual visual = new System.Windows.Media.DrawingVisual();

            using (System.Windows.Media.DrawingContext dc = visual.RenderOpen())

            {

                // Fondo blanco del lienzo

                dc.DrawRectangle(System.Windows.Media.Brushes.White, null, new System.Windows.Rect(0, 0, 1100, 700));



                double currentAngle = 0;

                double centerX = 300, centerY = 350, radius = 220;

                double pixelsPerDip = System.Windows.Media.VisualTreeHelper.GetDpi(visual).PixelsPerDip;



                var brushes = new List<System.Windows.Media.Brush> {

            System.Windows.Media.Brushes.DarkSlateBlue, System.Windows.Media.Brushes.RoyalBlue,

            System.Windows.Media.Brushes.MediumPurple, System.Windows.Media.Brushes.Teal,

            System.Windows.Media.Brushes.CadetBlue, System.Windows.Media.Brushes.SteelBlue,

            System.Windows.Media.Brushes.SkyBlue, System.Windows.Media.Brushes.LightSlateGray

        };



                for (int i = 0; i < data.Count; i++)

                {

                    double sliceAngle = (data[i].Count / total) * 360;

                    var figure = new System.Windows.Media.PathFigure { StartPoint = new System.Windows.Point(centerX, centerY), IsClosed = true };

                    double startRad = currentAngle * Math.PI / 180;

                    double endRad = (currentAngle + sliceAngle) * Math.PI / 180;



                    figure.Segments.Add(new System.Windows.Media.LineSegment(new System.Windows.Point(centerX + Math.Cos(startRad) * radius, centerY + Math.Sin(startRad) * radius), true));

                    figure.Segments.Add(new System.Windows.Media.ArcSegment(new System.Windows.Point(centerX + Math.Cos(endRad) * radius, centerY + Math.Sin(endRad) * radius), new System.Windows.Size(radius, radius), sliceAngle, sliceAngle > 180, System.Windows.Media.SweepDirection.Clockwise, true));

                    dc.DrawGeometry(brushes[i % brushes.Count], new System.Windows.Media.Pen(System.Windows.Media.Brushes.White, 1), new System.Windows.Media.PathGeometry(new[] { figure }));



                    // Leyenda de tamaño grande (20px)

                    double ly = 100 + (i * 50);

                    dc.DrawRectangle(brushes[i % brushes.Count], null, new System.Windows.Rect(650, ly, 30, 30));



                    var text = new System.Windows.Media.FormattedText(

                        $"{data[i].Label}: {data[i].Count} ({(data[i].Count / total * 100):F1}%)",

                        System.Globalization.CultureInfo.InvariantCulture, System.Windows.FlowDirection.LeftToRight,

                        new System.Windows.Media.Typeface("Verdana"), 20, System.Windows.Media.Brushes.Black, pixelsPerDip);



                    dc.DrawText(text, new System.Windows.Point(700, ly - 5));

                    currentAngle += sliceAngle;

                }

            }



            var bmp = new RenderTargetBitmap(1100, 700, 96, 96, System.Windows.Media.PixelFormats.Pbgra32);

            bmp.Render(visual);

            var encoder = new PngBitmapEncoder();

            encoder.Frames.Add(BitmapFrame.Create(bmp));



            using (var ms = new System.IO.MemoryStream())

            {

                encoder.Save(ms);

                return ms.ToArray();

            }

        }



        private async Task RefreshServer()



        {



            string url = "http://10.10.16.78:8080/api/refresh";



            try



            {



                using (HttpClient client = new HttpClient())



                {



                    var response = await client.PostAsync(url, null);



                    if (response.IsSuccessStatusCode)



                    {



                        MessageBox.Show("¡Servidor refrescado correctamente!", "Éxito");



                        await LoadAllData();



                    }



                }



            }



            catch (Exception ex)



            {



                MessageBox.Show("No se pudo conectar con Java: " + ex.Message);



            }



        }







        public event PropertyChangedEventHandler PropertyChanged;



        protected void OnPropertyChanged([CallerMemberName] string name = null) => PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));



    }







    public class UsersViewModel : INotifyPropertyChanged



    {



        private ObservableCollection<User> _users;



        public ObservableCollection<User> Users { get => _users; set { _users = value; OnPropertyChanged(); } }



        public UsersViewModel() { Users = new ObservableCollection<User>(); }



        public async Task LoadData()



        {



            try



            {



                using (HttpClient client = new HttpClient())



                {



                    client.Timeout = TimeSpan.FromSeconds(10);



                    string json = await client.GetStringAsync("http://10.10.16.78:8080/users?size=2000");



                    var response = JsonConvert.DeserializeObject<ApiResponse<User>>(json);



                    if (response?.Items != null)



                        Users = new ObservableCollection<User>(response.Items.Where(u => !string.IsNullOrEmpty(u.Email)));



                }



            }



            catch { }



        }



        public event PropertyChangedEventHandler PropertyChanged;



        protected void OnPropertyChanged([CallerMemberName] string name = null) => PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));



    }







    public class CamerasViewModel : INotifyPropertyChanged



    {



        private List<Camera> _masterList = new List<Camera>();

        public List<Camera> AllCameras => _masterList; // Guardar todas las cámaras en una lista publica para generar pdf

        private List<Camera> _filteredList = new List<Camera>();







        private ObservableCollection<Camera> _cameras;



        public ObservableCollection<Camera> Cameras { get => _cameras; set { _cameras = value; OnPropertyChanged(); } }







        private string _searchText;



        public string SearchText



        {



            get => _searchText;



            set { _searchText = value; OnPropertyChanged(); ApplyFilter(); }



        }







        private int _currentPage = 1;



        private int _itemsPerPage = 10;



        private string _pageInfo;



        public string PageInfo { get => _pageInfo; set { _pageInfo = value; OnPropertyChanged(); } }







        public ICommand RefreshCommand { get; }



        public ICommand AddCameraCommand { get; }



        public ICommand EditCameraCommand { get; }



        public ICommand DeleteCameraCommand { get; }



        public ICommand ViewImageCommand { get; }



        public ICommand NextPageCommand { get; }



        public ICommand PreviousPageCommand { get; }







        private readonly DatabaseService _dbService;







        public CamerasViewModel()



        {



            _dbService = new DatabaseService();



            Cameras = new ObservableCollection<Camera>();







            RefreshCommand = new RelayCommand(async o => await LoadData());







            NextPageCommand = new RelayCommand(o => { if (_currentPage * _itemsPerPage < _filteredList.Count) { _currentPage++; UpdateView(); } });



            PreviousPageCommand = new RelayCommand(o => { if (_currentPage > 1) { _currentPage--; UpdateView(); } });







            AddCameraCommand = new RelayCommand(async o =>
            {
                CameraFormWindow win = new CameraFormWindow();
                if (win.ShowDialog() == true)
                {
                    var newCamera = win.CameraData;

                    // --- FIX 1: FORZAR ID A 0 ---
                    // Esto evita el error "Conflict" o que intente actualizar en vez de insertar
                    newCamera.CameraId = 0;
                    newCamera.SourceId = 67;

                    if (await _dbService.InsertCamera(newCamera))
                        await LoadData();
                }
            });







            EditCameraCommand = new RelayCommand(async o =>
            {
                if (o is Camera c)
                {
                    // --- FIX 2: CLONAR EL OBJETO ---
                    // Usamos JSON para crear una copia exacta desconectada de la lista original
                    var json = JsonConvert.SerializeObject(c);
                    var cameraClone = JsonConvert.DeserializeObject<Camera>(json);

                    // Pasamos el CLON a la ventana, no el original 'c'
                    CameraFormWindow win = new CameraFormWindow(cameraClone);

                    if (win.ShowDialog() == true)
                    {
                        // Enviamos el clon modificado a la base de datos
                        if (await _dbService.UpdateCamera(win.CameraData))
                        {
                            // Solo si la API responde "OK", recargamos la lista
                            await LoadData();
                        }
                    }
                }
            });







            DeleteCameraCommand = new RelayCommand(async o =>



            {



                if (o is Camera c && MessageBox.Show($"¿Eliminar cámara '{c.Name}'?", "Confirmar", MessageBoxButton.YesNo) == MessageBoxResult.Yes)



                {



                    if (await _dbService.DeleteCamera(c.SourceId, c.CameraId)) await LoadData();



                }



            });







            ViewImageCommand = new RelayCommand(o =>



            {



                string url = (o is Camera c) ? c.Url : o as string;



                if (!string.IsNullOrEmpty(url)) new ImageWindow(url).Show();



            });



        }







        public async Task LoadData()
        {
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    client.Timeout = TimeSpan.FromSeconds(10);
                    string json = await client.GetStringAsync("http://10.10.16.78:8080/cameras?size=2000");

                    var response = JsonConvert.DeserializeObject<ApiResponse<Camera>>(json);

                    // Verificamos que no sea null para evitar crash
                    _masterList = response?.Items?.Where(c => !string.IsNullOrEmpty(c.Name)).ToList() ?? new List<Camera>();
                }
            }
            catch (Exception ex)
            {
                // --- FIX 3: VER EL ERROR ---
                // Esto te dirá por qué deja de cargar la lista
                MessageBox.Show($"Error al cargar datos: {ex.Message}", "Error de Conexión");
            }

            ApplyFilter();
        }







        private void ApplyFilter()



        {



            _filteredList = string.IsNullOrWhiteSpace(SearchText) ? new List<Camera>(_masterList) : _masterList.Where(c => c.Name != null && c.Name.ToLower().Contains(SearchText.ToLower())).ToList();



            _currentPage = 1; UpdateView();



        }







        private void UpdateView()



        {



            var pagedData = _filteredList.Skip((_currentPage - 1) * _itemsPerPage).Take(_itemsPerPage).ToList();



            Cameras = new ObservableCollection<Camera>(pagedData);



            int totalPages = (int)Math.Ceiling((double)_filteredList.Count / _itemsPerPage);



            PageInfo = $"Página {_currentPage} de {(totalPages == 0 ? 1 : totalPages)}";



        }







        public event PropertyChangedEventHandler PropertyChanged;



        protected void OnPropertyChanged([CallerMemberName] string name = null) => PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));



    }







    public class IncidenceViewModel : INotifyPropertyChanged



    {



        private List<Incidence> _masterList = new List<Incidence>();

        public List<Incidence> AllIncidences => _masterList; // Guardar todas las incidencias en una lista publica para generar pdf

        private List<Incidence> _filteredList = new List<Incidence>();







        private ObservableCollection<Incidence> _incidences;



        public ObservableCollection<Incidence> Incidences { get => _incidences; set { _incidences = value; OnPropertyChanged(); } }







        private string _searchText;



        public string SearchText { get => _searchText; set { _searchText = value; OnPropertyChanged(); ApplyFilter(); } }



        public ObservableCollection<string> FilterOptions { get; set; } = new ObservableCollection<string>

        {

        "Todos", "Accidente", "Obras", "Meteorología", "Seguridad vial",

        "Retención", "Vialidad invernal", "Puertos de montaña", "Otros"

        };



        private string _currentFilter = "Todos";

        public string CurrentFilter

        {

            get => _currentFilter;

            set

            {

                if (_currentFilter != value)

                {

                    _currentFilter = value;

                    OnPropertyChanged(); // Notifica a la UI del cambio

                    ApplyFilter();      // Llama a la función que filtra la tabla

                }

            }

        }







        private int _currentPage = 1;



        private int _itemsPerPage = 10;



        private string _pageInfo;



        public string PageInfo { get => _pageInfo; set { _pageInfo = value; OnPropertyChanged(); } }







        public ICommand AddIncidenceCommand { get; }



        public ICommand EditIncidenceCommand { get; }



        public ICommand DeleteIncidenceCommand { get; }



        public ICommand RefreshCommand { get; }



        public ICommand NextPageCommand { get; }



        public ICommand PreviousPageCommand { get; }







        private readonly DatabaseService _dbService;







        public IncidenceViewModel()



        {



            _dbService = new DatabaseService();



            Incidences = new ObservableCollection<Incidence>();







            RefreshCommand = new RelayCommand(async o => await LoadData());



            NextPageCommand = new RelayCommand(o => { if (_currentPage * _itemsPerPage < _filteredList.Count) { _currentPage++; UpdateView(); } });



            PreviousPageCommand = new RelayCommand(o => { if (_currentPage > 1) { _currentPage--; UpdateView(); } });







            AddIncidenceCommand = new RelayCommand(async o =>



            {



                IncidenceFormWindow win = new IncidenceFormWindow();



                if (win.ShowDialog() == true)



                {



                    win.IncidenceData.SourceId = 67;



                    if (await _dbService.InsertIncidence(win.IncidenceData)) await LoadData();



                }



            });







            EditIncidenceCommand = new RelayCommand(async o =>



            {



                if (o is Incidence i)



                {



                    IncidenceFormWindow win = new IncidenceFormWindow(i);



                    if (win.ShowDialog() == true)



                    {



                        if (await _dbService.UpdateIncidence(win.IncidenceData)) await LoadData();



                    }



                }



            });







            DeleteIncidenceCommand = new RelayCommand(async o =>



            {



                if (o is Incidence i && MessageBox.Show("¿Eliminar?", "Confirmar", MessageBoxButton.YesNo) == MessageBoxResult.Yes)



                {



                    if (await _dbService.DeleteIncidence(i.IncidenceId)) await LoadData();



                }



            });



        }







        public async Task LoadData()



        {



            try



            {



                using (HttpClient client = new HttpClient())



                {



                    string json = await client.GetStringAsync("http://10.10.16.78:8080/incidences?size=2000");



                    var res = JsonConvert.DeserializeObject<ApiResponse<Incidence>>(json);



                    _masterList = res?.Items?.Where(i => !string.IsNullOrEmpty(i.Type)).ToList() ?? new List<Incidence>();



                }



            }



            catch { }



            ApplyFilter();



        }







        private void ApplyFilter()



        {



            var query = _masterList.AsEnumerable();



            if (CurrentFilter != "Todos") query = query.Where(i => i.Type.Equals(CurrentFilter, StringComparison.OrdinalIgnoreCase));



            if (!string.IsNullOrWhiteSpace(SearchText)) query = query.Where(i => i.Road.ToLower().Contains(SearchText.ToLower()));



            _filteredList = query.ToList(); _currentPage = 1; UpdateView();



        }







        private void UpdateView()



        {



            var paged = _filteredList.Skip((_currentPage - 1) * _itemsPerPage).Take(_itemsPerPage).ToList();



            Incidences = new ObservableCollection<Incidence>(paged);



            int total = (int)Math.Ceiling((double)_filteredList.Count / _itemsPerPage);



            PageInfo = $"Página {_currentPage} de {(total == 0 ? 1 : total)}";



        }



        public event PropertyChangedEventHandler PropertyChanged;



        protected void OnPropertyChanged([CallerMemberName] string name = null) => PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));



    }



    public class RelayCommand : ICommand



    {



        private readonly Action<object> _execute;

        private readonly Predicate<object> _canExecute;

        public RelayCommand(Action<object> execute, Predicate<object> canExecute = null) { _execute = execute; _canExecute = canExecute; }

        public bool CanExecute(object parameter) => _canExecute == null || _canExecute(parameter);

        public void Execute(object parameter) => _execute(parameter);

        public event EventHandler CanExecuteChanged { add { CommandManager.RequerySuggested += value; } remove { CommandManager.RequerySuggested -= value; } }

    }
}