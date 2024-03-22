import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.nio.charset.StandardCharsets;

public class Panel extends JPanel {

    public Panel(JFrame frame) {

        //éléments de gui
        JButton btnGenere = new JButton();
        JButton btnTrad = new JButton();
        JColorChooser colorChooser = new JColorChooser(Color.BLUE);
        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
        int i = 0;
        for (AbstractColorChooserPanel accp : panels) {
            if (i!=0) colorChooser.removeChooserPanel(accp);
            i+=1;
	}
        JTextField inputCode = new JTextField();
        JPanel couleurAff = new JPanel();
        JLabel labelRouge = new JLabel("Rouge :");
        JLabel labelVert = new JLabel("Vert : ");
        JLabel labelBleu = new JLabel("Bleu : ");   
        JLabel labelAlpha = new JLabel("Alpha : ");

        setPreferredSize(new Dimension(1200, 600));
        setLayout(null);

        
        btnGenere.setAction(new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

		    //generation des chiffres du code barre
		    ArrayList<String> t = CodeBarre.generateCodeBarre(colorChooser.getColor());
		
		    //generation et ajout du code barre
		    JPanel p = CodeBarre.getCodeBarre(t);
		    add(p);
		    p.paint(getGraphics());
		    inputCode.setText(t.get(1).replace(" ",""));
		}
	    });
        btnTrad.setAction(new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
		    if (inputCode.getText().length()==11 && CodeBarre.controle(inputCode.getText())) {
			//conversion du code decimal en binaire
			String x = CodeBarre.barcodeToBinary(inputCode.getText());

			//conversion du code binaire en couleur
			Color c = CodeBarre.codeToColor(x);
			
			labelRouge.setText("Rouge : "+c.getRed());
			labelBleu.setText("Bleu : "+c.getBlue());
			labelVert.setText("Vert : "+c.getGreen());
			labelAlpha.setText("Alpha : "+c.getAlpha());
			couleurAff.setBackground(c);
		    }
		}
	    });
        btnGenere.setText("Générer");
        btnTrad.setText("Traduire");

        //on ajoute tous les objets au panneau
        add(btnGenere);
        add(colorChooser);
        add(btnTrad);
        add(couleurAff);
        add(labelRouge);
        add(labelVert);
        add(labelBleu);
        add(labelAlpha);
        add(inputCode);

        //On met en place les objets (taille et emplacement)
        btnGenere.setBounds(235, 295, 140, 20);
        colorChooser.setBounds(25, 30, 545, 255);
        couleurAff.setBounds(625, 325, 545, 245);
        btnTrad.setBounds(830, 195, 140, 20);
        labelRouge.setBounds(645, 245, 100, 25);
        labelVert.setBounds(790, 245, 100, 25);
        labelBleu.setBounds(935, 245, 100, 25);
        labelAlpha.setBounds(1065, 245, 100, 25);
        inputCode.setBounds(765, 95, 285, 30);

    }
}

