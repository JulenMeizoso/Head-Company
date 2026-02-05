using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using Newtonsoft.Json;

namespace RETO2HC
{
    public class DatabaseService
    {
        private readonly string _baseUrl = "http://10.10.16.78:8080";
        private static readonly HttpClient _client = new HttpClient();

        public DatabaseService()
        {
            if (_client.BaseAddress == null)
            {
                _client.BaseAddress = new Uri(_baseUrl);
                _client.Timeout = TimeSpan.FromSeconds(10);
            }
        }

        // --- MÉTODOS PARA CÁMARAS ---

        public async Task<bool> InsertCamera(Camera c)
        {
            try
            {
                string json = JsonConvert.SerializeObject(c);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                var response = await _client.PostAsync($"{_baseUrl}/cameras", content);
                return response.IsSuccessStatusCode;
            }
            catch { return false; }
        }

        public async Task<bool> UpdateCamera(Camera c)
        {
            try
            {
                string json = JsonConvert.SerializeObject(c);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                // Endpoint: /cameras/{sourceId}/{cameraId}
                var response = await _client.PutAsync($"{_baseUrl}/cameras/{c.SourceId}/{c.CameraId}", content);
                return response.IsSuccessStatusCode;
            }
            catch { return false; }
        }

        public async Task<bool> DeleteCamera(int sourceId, int cameraId)
        {
            try
            {
                var response = await _client.DeleteAsync($"{_baseUrl}/cameras/{sourceId}/{cameraId}");
                return response.IsSuccessStatusCode;
            }
            catch { return false; }
        }

        // --- MÉTODOS PARA INCIDENCIAS ---

        public async Task<bool> InsertIncidence(Incidence i)
        {
            try
            {
                string json = JsonConvert.SerializeObject(i);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                var response = await _client.PostAsync($"{_baseUrl}/incidences", content);
                return response.IsSuccessStatusCode;
            }
            catch { return false; }
        }

        public async Task<bool> UpdateIncidence(Incidence i)
        {
            try
            {
                string json = JsonConvert.SerializeObject(i);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                // Endpoint: /incidences/{incidenceId}
                var response = await _client.PutAsync($"{_baseUrl}/incidences/{i.IncidenceId}", content);
                return response.IsSuccessStatusCode;
            }
            catch { return false; }
        }

        public async Task<bool> DeleteIncidence(int id)
        {
            try
            {
                var response = await _client.DeleteAsync($"{_baseUrl}/incidences/{id}");
                return response.IsSuccessStatusCode;
            }
            catch { return false; }
        }
    }
}