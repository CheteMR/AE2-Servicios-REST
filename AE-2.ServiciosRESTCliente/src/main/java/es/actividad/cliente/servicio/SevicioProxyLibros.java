package es.actividad.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.actividad.cliente.entidad.Libros;

@Service
public class SevicioProxyLibros {
	
	public static final String URL = "http://localhost:8080/libros/";
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Libros obtener(int id) {
		try {
			ResponseEntity<Libros> re = restTemplate.getForEntity(URL + id, Libros.class);
			HttpStatusCode hs= re.getStatusCode();
			if(hs == HttpStatus.OK) {
				return re.getBody();
			}else {
				System.out.println("Respuesta no válida");
				return null;
				
			}
		}catch (HttpClientErrorException e) {
			System.out.println("El libro no se ha encontrado, id: " + id);
		    System.out.println("Codigo de respuesta: " + e.getStatusCode());
		    return null;
				
			}
		}
	
	public Libros alta(Libros l){
		
		try {
			
			ResponseEntity<Libros> re = restTemplate.postForEntity(URL, l, Libros.class);
			System.out.println("alta -> Codigo de respuesta " + re.getStatusCode());
			return re.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("alta -> La persona NO se ha dado de alta, id: " + l);
		    System.out.println("alta -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	public boolean modificar(Libros l) {
		try {
			restTemplate.put(URL + l.getId(), l, Libros.class);
			return true;
			
		}catch (HttpClientErrorException e) {
			System.out.println("El libro no se ha modificado, id: " + l.getId());
			System.out.println("Código de respuesta: " + e.getStatusCode());
			return false;
		}
	}
	
	public boolean borrar (int id) {
		try {
		restTemplate.delete(URL + id);
		return true;
	} catch (HttpClientErrorException e) {
		System.out.println("El libro no se borra, id: " +id);
		System.out.println("Código de respuesta: " + e.getStatusCode());
		return false;
		
	     }
     }
	
	public List<Libros> listar(String titulo){
		String queryParams = "";		
		if(titulo != null) {
			queryParams += "?nombre=" + titulo;
		}
		
		try {
			//Ej http://localhost:8080/personas?nombre=harry GET
			ResponseEntity<Libros[]> response =
					  restTemplate.getForEntity(URL + queryParams,Libros[].class);
			Libros[] arrayPersonas = response.getBody();
			return Arrays.asList(arrayPersonas);//convertimos el array en un ArrayList
		} catch (HttpClientErrorException e) {
			System.out.println("listar -> Error al obtener la lista de personas");
		    System.out.println("listar -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
}



