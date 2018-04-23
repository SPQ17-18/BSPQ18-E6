package es.deusto.bspq18.e6.DeustoBox.Server.assembler;

import java.util.ArrayList;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public class Assembler {

	public DUserDTO userDTO(DUser user) {
		DUserDTO userdto =  new DUserDTO(user.getEmail(), user.getUsername(), user.getPassword(), user.getRegisterDate());
		System.out.println(userdto.toString());
		userdto.setFiles(user.getFiles());
		return userdto;
	}
	
	public DUser user(DUserDTO userdto) {
		DUser user = new DUser(userdto.getEmail(), userdto.getUsername(), userdto.getPassword());
		user.setFiles(userdto.getFiles());
		return user;
	}
	
	public DFileDTO createFileDTO (DFile file, String path){	
	DFileDTO dto = new DFileDTO(file.getUser().getEmail(), path, file.getLastModified());
	return dto;
		
	}
	
	
	public ArrayList<DFileDTO> createFilesDTO(ArrayList<DFile> files, String path){
		ArrayList<DFileDTO> filesDTO = new ArrayList<DFileDTO>();
		for(int i = 0; i< files.size(); i++){
			String pathFile = path + files.get(i).getName().substring(1);
			filesDTO.add(createFileDTO (files.get(i), pathFile));
			}
		
		return filesDTO;
	}
}
