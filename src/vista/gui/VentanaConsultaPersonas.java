package vista.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador;
import modelo.operaciones.Procesos;
import modelo.vo.EstudianteVO;

public class VentanaConsultaPersonas extends JFrame {

	private JPanel panelPrincipal;
	private JTable tablaPersonas;
	DefaultTableModel modelo;
	private Coordinador miCoordinador;

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}
	
	public VentanaConsultaPersonas() {
		setSize(507, 343);
		setTitle("CALCULO DE PROMEDIO");
		setLocationRelativeTo(null);
		setResizable(false);
		iniciarComponentes();
		
	}
	
	private void iniciarComponentes(){
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JLabel lblTitulo = new JLabel("MOSTRAR PERSONAS");
		lblTitulo.setBackground(Color.black);
		lblTitulo.setForeground(Color.white);
		lblTitulo.setOpaque(true);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitulo.setBounds(0, 0, 499, 50);
		panelPrincipal.add(lblTitulo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 473, 236);
		panelPrincipal.add(scrollPane);
		
		tablaPersonas = new JTable();
		

		
		scrollPane.setViewportView(tablaPersonas);

	}

	private void crearModelo() {
		
		modelo=new DefaultTableModel();
		modelo.addColumn("Documento");
		modelo.addColumn("Nombre");
		modelo.addColumn("Nota1");
		modelo.addColumn("Nota2");
		modelo.addColumn("Nota3");
		modelo.addColumn("Promedio");
		
		tablaPersonas.setModel(modelo);
	}

	public void llenarTabla() {
		ArrayList<EstudianteVO> listaPersonas=miCoordinador.getListaPersonas();
		
		crearModelo();
		
		for (EstudianteVO p: listaPersonas) {
			Object[] fila=new Object[6];
			fila[0]=p.getDocumento();
			fila[1]=p.getNombre();
			fila[2]=p.getNota1();
			fila[3]=p.getNota2();
			fila[4]=p.getNota3();
			fila[5]=p.getPromedio();
			modelo.addRow(fila);
		}
	}
	

}










