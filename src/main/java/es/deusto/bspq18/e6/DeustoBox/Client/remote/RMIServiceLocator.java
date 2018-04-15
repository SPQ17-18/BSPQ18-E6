package es.deusto.bspq18.e6.DeustoBox.Client.remote;

import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;

public class RMIServiceLocator {

	private IDeustoBoxRemoteService façade;

	public void setService(String[] args) {

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		System.out.println(name);
		try {
			this.façade = (IDeustoBoxRemoteService) java.rmi.Naming.lookup(name);
		} catch (Exception ex) {

			System.out.println("An error has happened while setting the service");
		}

	}

	public IDeustoBoxRemoteService getService() {
		return this.façade;

	}

}