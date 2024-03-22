import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;

public class CodeBarre {
    public static void main(String[] args) {

	//éléments de gui
        JFrame frame = new JFrame("Code Barre");
        Panel panel = new Panel(frame);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(1200, 600);


        frame.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e)
		{
		    System.exit(0);
		}
	    });

    }

    //fonction prenant une couleur en argument et renvoie le code sous la forme d'un ArrayList<String>
    public static ArrayList<String> generateCodeBarre (Color c){
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();

        String codeBinaire =  "";
        String codeSansSeparateur ="";

        codeBinaire+="101010";
        String[] separateurs = new String[4];

	String sa = getBinary(a);
	String sr = getBinary(r);
	String sg = getBinary(g);
	String sb = getBinary(b);
	separateurs[0] = getSeparator(sa);
	separateurs[1] = getSeparator(sr);
	separateurs[2] = getSeparator(sg);
	separateurs[3] = getSeparator(sb);

	codeBinaire += sa;
	codeBinaire+= separateurs[0];
	codeBinaire += sr;
	codeBinaire+= separateurs[1];
	codeBinaire += sg;
	codeBinaire+= separateurs[2];
	codeBinaire += sb;
	codeSansSeparateur += sa;
	codeSansSeparateur += sr;
	codeSansSeparateur += sg;
	codeSansSeparateur += sb;

        Long codeSsDec = Long.parseLong(codeSansSeparateur, 2);
        System.out.println("code : " + codeSsDec);
        int key = getKey(String.valueOf(codeSsDec));
	System.out.println("key:"+key);
        String keyBinary = getBinary(key);
        ArrayList<String> res = new ArrayList<>();
        codeBinaire+= keyBinary;
        codeBinaire+= separateurs[3];
        res.add(codeBinaire);
        res.add(codeDecSeparateur(String.valueOf(codeSsDec), key));
        System.out.println(" aaaa : " + String.valueOf(codeSsDec));
        System.out.println();
        return res;
    }

    //Fonction prenant en argument le code et sa clé et renvoie le code avec les séparateurs
    public static String codeDecSeparateur (String code, int key){
        StringBuilder tmp = new StringBuilder(code);
        while (code.length() < 9){
            tmp.insert(0, "0");
        }
        String res = "";
        for (int i = 0; i < 4; i++) {
            if(i==3) res+=tmp.charAt(i*3);
            else res = res + tmp.charAt(i*3)+tmp.charAt(i*3+1)+tmp.charAt(i*3+2);
            System.out.println(res);
            res += "  ";
        }
        res = res + key;
        return res;
    }

    //Fonction qui renvoie le bon séparateur
    public static String getSeparator (String precedent){
	if (precedent.substring(2).equals("101010")) return "010101";
	else return "101010";
    }

    //Fonction qui renvoie un entier en base 2 en String 
    public static String getBinary (int i){
        StringBuilder res = new StringBuilder(Integer.toBinaryString(i));
	while (res.length() < 8){
	    res.insert(0, "0");
	}
        return res.toString();
    }

    //Fonction qui renvoie la clé de correction du code
    public static int getKey(String code){
        int s=0;
        for (int i = 0; i < code.length(); i++) {
            int val = Integer.parseInt(code.charAt(i) + "");
            s = i % 2 == 0 ? val + s : val * 3 +s   ;
        }
        int res = 10 - (s%10);
	if (res == 10) return 0;
	else return res;
    }

    //Fonction qui vérifie si le code est correct en comparant sa clé actuelle et la clé réelle
    public static boolean controle(String code){
	String key = code.substring(10);
	return Integer.parseInt(key)==getKey(code.substring(0, 10));
    }

    //Fonction qui dessine le code barre
    public static JPanel getCodeBarre(ArrayList<String> codes){
        JPanel res = new JPanel()  {
		@Override
		public void paintComponent(Graphics g) {
		    int x = 25;
		    Graphics2D g2 = (Graphics2D) g;
		    Rectangle fond = new Rectangle(25, 325, 528, 245);
		    g2.setColor(new Color(162, 162, 162));
		    g2.draw(fond);
		    g2.fill(fond);
		    for (int i = 0; i < codes.get(0).length(); i++) {
			if (codes.get(0).charAt(i) == '0'){
			    x+=8;
			} else {
			    Rectangle rectangle = new Rectangle(x, 330, 8, 200) ;
			    g2.setColor(Color.BLACK);
			    g2.draw(rectangle);
			    g2.fill(rectangle);
			    x+=8;
			}
		    }
		}
	    };
        String c = codes.get(1);

        JLabel m = new JLabel(c);
        m.setBounds(180, 325, 320, 440);
        m.setFont(m.getFont().deriveFont(20.0f));
        res.add(m);
        res.setBounds(25, 325, 545, 600);
        return res;
    }

    //Fonction qui prend un code et le renvoie en binaire en String 
    public static String barcodeToBinary(String barcode) {
	String keyLess = barcode.substring(0,barcode.length()-1); 
	Long decBase = Long.parseLong(keyLess, 10);
	return Long.toBinaryString(decBase); 
    }

    //Fonction qui prend un code binaire et renvoie sa couleur
    public static Color codeToColor(String barcodeBinary) {
        int[] values = new int[4];
	String current = "";
        for (int i = 0; i < barcodeBinary.length(); i+=1) {
	    if (i%8==0 && i!=0) {
		values[i/8-1] = Integer.parseInt(current, 2);
		current = "";
	    }
	    current+=barcodeBinary.charAt(i);
	}
	values[3] = Integer.parseInt(current, 2);
	System.out.println(Arrays.toString(values));
	return new Color(values[1],values[2],values[3],values[0]);
    }
}
