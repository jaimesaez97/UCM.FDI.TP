// TERMINAR PARSEAOPCIONMODO()
// ¿a EJECUTA SE LE PASA INT PASOS?

package es.ucm.fdi.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import control.Controlador;
import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.CarAlreadyAdded;
import es.ucm.fdi.exception.ErrorDeSimulacion;
import es.ucm.fdi.exception.JunctionAlreadyAdded;
import es.ucm.fdi.exception.NotValidItinerary;
import es.ucm.fdi.exception.NotValidSpeed;
import es.ucm.fdi.exception.RoadAlreadyAdded;
import es.ucm.fdi.simulador.SimuladorTrafico;
import vista.VentanaPrincipal;

public class Main {


	private final static Integer limiteTiempoPorDefecto = 10;
	private static Integer limiteTiempo = null;
	private static String ficheroEntrada = null;
	private static String ficheroSalida = null;
	@SuppressWarnings("unused")
	private static File[] files;
	private static ModoEjecucion mode;
		

  				  //PARA PROBAR EJEMPLOS DE FICHEROS
	private static void ejecutaFicheros(String path) throws IOException, ErrorDeSimulacion, JunctionAlreadyAdded, CantFindOnMap, CarAlreadyAdded, RoadAlreadyAdded, NotValidItinerary, NotValidSpeed, InvocationTargetException, InterruptedException {

		File dir = new File(path);
		// path = /home/jaime/Documentos/WORKSPACES/WORKSPACE JAVA/Pr4/ejemplos/basic/

		if ( !dir.exists() ) {
			throw new FileNotFoundException(path);
		}
			
		File[] files = dir.listFiles(new FilenameFilter() {
			//@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});
		for (File file : files) {
			Main.ficheroEntrada = file.getAbsolutePath();
			Main.ficheroSalida = file.getAbsolutePath() + ".out";
			Main.limiteTiempo = 10;
			Main.iniciaModoEstandar();
		}

		String[] args = {};
		Comprobador.main(args);

	}

	private static void iniciaModoGrafico() throws FileNotFoundException, InvocationTargetException, InterruptedException{
		SimuladorTrafico sim = new SimuladorTrafico();
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
			// CAMBIAR SI QUIERO CAMBIAR PASOS
		Controlador ctrl = new Controlador(sim, Main.limiteTiempoPorDefecto, null, os);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new VentanaPrincipal(Main.ficheroEntrada, ctrl);
				} catch (FileNotFoundException e) { 
					e.printStackTrace();	
				}
			}
		});
	}
	
	private static void ParseaArgumentos(String[] args) {

		// define the valid command line options
		//
		Options opcionesLineaComandos = Main.construyeOpciones();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args);
			parseaOpcionHELP(linea, opcionesLineaComandos);
			parseaOpcionFicheroIN(linea);
			parseaOpcionFicheroOUT(linea);
			parseaOpcionSTEPS(linea);
			parseaOpcionModo(linea);
			
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] resto = linea.getArgs();
			if (resto.length > 0) {
				String error = "Illegal arguments:";
				for (String o : resto)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		

	}
		
	private static void parseaOpcionModo(CommandLine linea) {
		if(linea.hasOption("m")) {
			switch(linea.getOptionValue("m")) {
				case "batch": Main.mode = ModoEjecucion.BATCH; break;
				case "gui":   Main.mode = ModoEjecucion.GUI; break;
			}			
		}
	}

	private static Options construyeOpciones() {
		Options opcionesLineacomandos = new Options();
			// añado opcion para modo
		opcionesLineacomandos.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Modo de vista.").build());
		opcionesLineacomandos.addOption(Option.builder("h").longOpt("help").desc("Muestra la ayuda.").build());
		opcionesLineacomandos.addOption(Option.builder("i").longOpt("input").hasArg().desc("Fichero de entrada de eventos.").build());
		opcionesLineacomandos.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Fichero de salida, donde se escriben los informes.").build());
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Pasos que ejecuta el simulador en su bucle principal (el valor por defecto es " + Main.limiteTiempoPorDefecto + ").")
				.build());

		return opcionesLineacomandos;
	}

	private static void parseaOpcionHELP(CommandLine linea, Options opcionesLineaComandos) {
		if (linea.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);
			System.exit(0);
		}
	}

	private static void parseaOpcionFicheroIN(CommandLine linea) throws ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		if (Main.ficheroEntrada == null) {
			throw new ParseException("El fichero de eventos no existe");
		}
	}

	private static void parseaOpcionFicheroOUT(CommandLine linea) throws ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
	}

	private static void parseaOpcionSTEPS(CommandLine linea) throws ParseException {
		String t = linea.getOptionValue("t", Main.limiteTiempoPorDefecto.toString());
		try {
			Main.limiteTiempo = Integer.parseInt(t);
			assert (Main.limiteTiempo < 0);
		} catch (Exception e) {
			throw new ParseException("Valor invalido para el limite de tiempo: " + t);
		}
	}

	private static void iniciaModoEstandar() throws IOException, ErrorDeSimulacion, JunctionAlreadyAdded, CantFindOnMap, CarAlreadyAdded, RoadAlreadyAdded, NotValidItinerary, NotValidSpeed {
		InputStream is = new FileInputStream(new File(Main.ficheroEntrada));
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		SimuladorTrafico sim = new SimuladorTrafico();
		Controlador ctrl = new Controlador(sim,Main.limiteTiempo,is,os);
		ctrl.cargaEventos(is);
		ctrl.ejecuta(120);
		is.close();
		System.out.println("Done!");
	}
	

	public static void main(String[] args) throws IOException, JunctionAlreadyAdded, CantFindOnMap, CarAlreadyAdded, RoadAlreadyAdded, NotValidItinerary, NotValidSpeed, InvocationTargetException, InterruptedException {

		Main.ParseaArgumentos(args);
		
		try {
			//ejecutaFicheros("C:/hlocal/workspace-4.4-64bits/Pr5/examples/advanced");
			//ejecutaFicheros("C:/Wspace/Pr5/examples/advanced");
			switch(Main.mode){
				case GUI: iniciaModoGrafico(); break;
				//default: ejecutaFicheros("C:/hlocal/Wspace/Prac6/examples/cruces_avanzados"); break;
				//default: ejecutaFicheros("C:/hlocal/workspace-4.4-64bits/Prac6/examples/advanced"); break;
				default: ejecutaFicheros("/home/jaime/Documentos/WORKSPACES/WORKSPACE JAVA/Prac6/examples/cruces_avanzados/");
			}
		} catch (ErrorDeSimulacion e) {
			e.printStackTrace();
		}
	
	}

}