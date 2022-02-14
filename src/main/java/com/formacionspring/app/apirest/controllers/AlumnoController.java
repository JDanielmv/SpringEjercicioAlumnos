package com.formacionspring.app.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacionspring.app.apirest.entity.Alumno;
import com.formacionspring.app.apirest.entity.Comunidad;
import com.formacionspring.app.apirest.service.AlumnoService;

@RestController
@RequestMapping("/api")
public class AlumnoController {
	
	@Autowired
	private AlumnoService servicio;
	
	@GetMapping("/alumnos")
	public List<Alumno> alumno(){
		return servicio.findAll();
	}
	
	
	@GetMapping("/alumnos/{id}")
	public ResponseEntity<?> alumnoShow(@PathVariable Long id){
		Alumno alumno = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			alumno = servicio.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta a la base de datos");
			response.put("error", e.getMessage().concat("_ ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(alumno == null) {
			response.put("mensaje", "El alumno ID: ".concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Alumno>(alumno,HttpStatus.OK);
	}
	
	
	
	@PostMapping("/alumnos")
	public ResponseEntity<?> saveCliente(@RequestBody Alumno alumno) {
		Alumno alumnoNew = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			alumnoNew = servicio.save(alumno);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar insert a la base de datos");
			response.put("error", e.getMessage().concat("_ ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El alumno ha sido creado con exito!");
		response.put("alumno", alumnoNew);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	
	
	@PutMapping("/alumnos/{id}")
	public ResponseEntity<?> updateAlumno(@RequestBody Alumno alumno,@PathVariable Long id) {
		Alumno alumnoActual = servicio.findById(id);
		
		Alumno alumnoUpdated = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			alumnoActual.setNombre(alumno.getNombre());
			alumnoActual.setApellido(alumno.getApellido());
			alumnoActual.setDni(alumno.getDni());
			alumnoActual.setEmail(alumno.getEmail());
			alumnoActual.setTelefono(alumno.getTelefono());
			alumnoActual.setDireccion(alumno.getDireccion());
			alumnoActual.setCp(alumno.getCp());
			
			servicio.save(alumnoActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar actualizar a la base de datos");
			response.put("error", e.getMessage().concat("_ ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje"," El cliente ha sido actualizado con exito!");
		response.put("cliente", alumnoActual);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	
	
	
	@DeleteMapping("/alumnos/{id}")
	public ResponseEntity<?> deleteAlumno(@PathVariable Long id) {
		Alumno alumnoBorrado = servicio.findById(id);
		
		Map<String,Object> response = new HashMap<>();
		
		try {
			servicio.delete(id);
			
			String nombreFotoAnterior = alumnoBorrado.getImagen();
			if(nombreFotoAnterior != null && nombreFotoAnterior.length()>0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoanterior = rutaFotoAnterior.toFile();
			
				if(archivoFotoanterior.exists() && archivoFotoanterior.canRead()) {
					archivoFotoanterior.delete();
				}
			
			}
			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al borrar el alumno con id " + id + " a la base de datos");
			response.put("error", e.getMessage().concat("_ ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje"," El alumno ha sido borrado con exito!");
		response.put("alumno", alumnoBorrado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	
	
	
	
	@PostMapping("/alumno/upload")
	public ResponseEntity<?> uploadImagen(@RequestParam("archivo")MultipartFile archivo,@RequestParam("id")Long id){
		
		Map<String,Object> response = new HashMap<>();
		
		Alumno alumno = servicio.findById(id);
		
		if(!archivo.isEmpty()) {
			//String nombreArchivo = archivo.getOriginalFilename();
			String nombreArchivo=UUID.randomUUID().toString()+"_"+ archivo.getOriginalFilename().replace("","");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
		
		try {
			Files.copy(archivo.getInputStream(), rutaArchivo);
		} catch (IOException e) {
			response.put("mensaje", "Error al borrar el alumno con id " + id + " a la base de datos");
			response.put("error", e.getMessage().concat("_ ".concat(e.getCause().getMessage())));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String nombreFotoAnterior = alumno.getImagen();
		if(nombreFotoAnterior != null && nombreFotoAnterior.length()>0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
			File archivoFotoanterior = rutaFotoAnterior.toFile();
		
			if(archivoFotoanterior.exists() && archivoFotoanterior.canRead()) {
				archivoFotoanterior.delete();
			}
		
		}
		
		alumno.setImagen(nombreArchivo);
		servicio.save(alumno);
		response.put("alumno", alumno);
		response.put("alumno","subida correcta de imagen " + nombreArchivo);
		
		}else {
			response.put("Archivo","archivo vacio");
		}
		
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	
	
	@GetMapping("/alumnos/uploads/img/{nombreImagen:.+}")
	public ResponseEntity<Resource> verImagen(@PathVariable String nombreImagen){
		
		Path rutaArchivo = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
		
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se puede cargar la imagen " + nombreImagen);
		}
		
		HttpHeaders cabecera = new HttpHeaders(); 
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}
	
	
	@GetMapping("/alumnos/comunidades")
	public List<Comunidad> listaComunidades(){
		return servicio.findAllComunidades();
	}
	
	
	

}
