using System;
using System.Windows;
using System.Windows.Media.Imaging;

namespace RETO2HC
{
    public partial class ImageWindow : Window
    {
        public ImageWindow(string imageUrl)
        {
            InitializeComponent();
            LoadImage(imageUrl);
        }

        private void LoadImage(string url)
        {
            if (string.IsNullOrEmpty(url))
            {
                StatusText.Text = "URL de imagen no disponible.";
                return;
            }

            try
            {
                // Crear la imagen desde la URL
                var bitmap = new BitmapImage();
                bitmap.BeginInit();
                bitmap.UriSource = new Uri(url, UriKind.Absolute);
                bitmap.CacheOption = BitmapCacheOption.OnLoad; // Carga inmediata en memoria
                bitmap.EndInit();

                DisplayImage.Source = bitmap;
                StatusText.Visibility = Visibility.Collapsed; // Ocultar texto de carga
            }
            catch (Exception ex)
            {
                StatusText.Text = "Error al cargar la imagen.";
                System.Diagnostics.Debug.WriteLine($"Error imagen: {ex.Message}");
            }
        }
    }
}