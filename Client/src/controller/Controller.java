private ArrayList<DFileDTO> filesDTO;

	public Controller(String[] args) throws RemoteException {
		rsl = new RMIServiceLocator();
		rsl.setService(args);
		new v_login(this);
		filesDTO = null;
		
	}
	
	

	public boolean signUp(String username, String email, String password) {
		DUserDTO res = null;
		try {
			res = rsl.getService().signUp(username, email, password);
		} catch (Exception ex) {
			System.err.println("- Exception " + ex.getMessage());
			ex.printStackTrace();
		}
		if (res != null) {
			this.userdto = res;
			return true;
		} else {
			return false;
		}
	}

	public boolean login(String email, String password) {
		DUserDTO res = null;
		try {
			res = rsl.getService().login(email, password);
		} catch (Exception ex) {
			return false;
		}

		if (res.equals(null)) {
			System.out.println("The user doesn't exist");
			return false;
		} else {
			this.userdto = res;
			return true;
		}

	}
	
	
	
	public void getFiles(){
		getListOfFiles(userdto.getEmail());
		
		File directorio=new File(this.path);
		if(!directorio.exists())
		directorio.mkdir();
		
		for(DFileDTO file : filesDTO){
		String pathFichero = this.path + file.getFile().getName();		
		File f1=new File(file.getFile().getPath());
		try {
			f1.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
}
		
		
		 try{
			 FileInputStream in=new FileInputStream(f1);
			 byte [] mydata=new byte[1024*1024];						
			 int mylen= in.read(mydata);
			 while(mylen>0){
				 rsl.getService().sendData(pathFichero, mydata, mylen);	 
				 mylen=in.read(mydata);				 
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		}
		 
		
	}
	
	public void getListOfFiles(String email){
		try {
			filesDTO = rsl.getService().getFiles(email);
		} catch (Exception ex) {
			
		}


	}
		
	public int getNumberOfFiles(){
	int files = 0;
	String email = userdto.getEmail();
	try {
		files = rsl.getService().getNumberOfUserFiles(email);
	} catch (Exception ex) {
	}
	return files;
	}
	
	public boolean passwordCorrect(String password) {
		boolean correct = false;
		try {
			correct = rsl.getService().checkPassword(userdto.getEmail(), password);
		} catch (Exception ex) {
		
		
	}
		return correct;
	}
	
	public boolean updatePassword (String password) {
		boolean correct = false;
		
		try {
			correct = rsl.getService().updatePassword(userdto.getEmail(), password);
		} catch (Exception ex) {
		
		
	}
		
		
		
		return correct;
	}

	public DUserDTO getUserdto() {
		return userdto;
	}

	public void setUserdto(DUserDTO userdto) {
		this.userdto = userdto;
	}

	public static void main(String[] args) throws RemoteException {
		new Controller(args);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path + getUserdto().getEmail() + "\\";
	}
	

}
