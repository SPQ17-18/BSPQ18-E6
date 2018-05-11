package es.deusto.bspq18.e6.DeustoBox.Server.assembler;

import java.util.ArrayList;
import java.util.Date;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.DConnectionDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DConnection;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public class Assembler {

	public DUserDTO createUserDTO(DUser user, String path) {
		DUserDTO userdto = new DUserDTO(user.getEmail(), user.getUsername(), user.getPassword(),
				user.getRegisterDate());
		userdto.setFiles(user.getFiles());
		return userdto;
	}

	public DUser createUser(DUserDTO userdto) {
		DUser user = new DUser( userdto.getUsername(),userdto.getEmail(), userdto.getPassword());
		user.setFiles(userdto.getFiles());
		return user;
	}

	public DFileDTO createFileDTO(DFile file, String path) {
		DUserDTO user = createUserDTO(file.getUser(), path);
		DFileDTO dto = new DFileDTO(user, file.getHash(), file.getName(), file.getLastModified(), path);
		return dto;
	}

	public DFile createFile(DFileDTO dto) {
		DFile file = new DFile(createUser(dto.getUser()), dto.getHash(), dto.getName(), dto.getLastModified());
		return file;
	}
	
	

	public ArrayList<DFileDTO> createFilesDTO(ArrayList<DFile> files, String path) {
		ArrayList<DFileDTO> filesDTO = new ArrayList<DFileDTO>();
		for (int i = 0; i < files.size(); i++) {
			String pathFile = path + files.get(i).getName().substring(1);
			filesDTO.add(createFileDTO(files.get(i), pathFile));
		}
		return filesDTO;
	}

	public ArrayList<DFile> createFiles(ArrayList<DFileDTO> filesDTO) {
		ArrayList<DFile> files = new ArrayList<DFile>();
		for (int i = 0; i < filesDTO.size(); i++) {
			files.add(createFile(filesDTO.get(i)));
		}
		return files;

	}
	
	public ArrayList<DConnectionDTO> createConnectionsDTO(ArrayList<DConnection> connections) {
		ArrayList<DConnectionDTO> connectionsDTO = new ArrayList<DConnectionDTO>();
		for (int i = 0; i < connections.size(); i++) {
			connectionsDTO.add(createConnectionDTO(connections.get(i)));
		}
		
		return connectionsDTO;
	}
	
	public DConnectionDTO createConnectionDTO(DConnection conection) {
		DConnectionDTO dto = new DConnectionDTO(conection.getID(), conection.getUserEmail(), conection.getConnectionDate(), conection.getOSUsed());
		return dto;
	}
}
