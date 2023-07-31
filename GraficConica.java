import java.awt.*;

import javafx.geometry.Point2D;

public class GraficConica extends Frame {
    Toolkit tool;
    int ww, hh;
    Image im;
    Graphics graphics;
    public PropPanel propPanel;
    public InputPanel inputPanel;
    public GraphicPanel graphicPanel;

    Font mainFont = new Font("TimesRoman", Font.BOLD, 18);
    Font headerFont = new Font("TimesRoman", Font.BOLD, 22);

    Color bgColor = Color.darkGray;
    Color componentColor = new Color(51, 51, 51);
    Color color1 = new Color(80, 180, 250);
    Color color2 = Color.white;
    Color color3 = Color.lightGray;
    Color graphicColor1 = Color.black;
    Color graphicColor2 = Color.blue;
    Color graphicColor3 = Color.red;

    public static void main(String[] args) {
        new GraficConica();
    }

    public GraficConica() {
        tool = getToolkit();
        Dimension res = tool.getScreenSize();
        ww = res.width;
        hh = res.height;
        setResizable(false);
        setTitle("Desenare Conica");
        setBackground(bgColor);
        setLayout(null);

        resize(ww, hh);
        move(0, 0);
        setVisible(true);

        im = createImage(ww, hh);
        graphics = im.getGraphics();

        inputPanel = new InputPanel(this);
        add(inputPanel);
        inputPanel.setBounds(25, 50, 300, hh - 80);

        propPanel = new PropPanel(this);
        add(propPanel);
        propPanel.setBounds(350, 50, 300, hh - 80);

        graphicPanel = new GraphicPanel(this);
        add(graphicPanel);
        graphicPanel.setBounds(675, 50, ww - 700, hh - 80);
    }

    public java.net.URL GetResources(String s) {
        return this.getClass().getResource(s);
    }

    public void paint(Graphics g) {
        g.drawImage(im, 0, 0, this);
    }

    public boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_DESTROY) {
            System.exit(0);
        }

        if (e.id == Event.ACTION_EVENT && e.target == inputPanel.startBtn ||
                e.id == Event.KEY_PRESS && e.key == Event.ENTER) {
            inputPanel.getCoefFromInputs();
            inputPanel.setEquation();

            propPanel.updateProp();
            graphicPanel.updateGraphic(); 
        }

        return super.handleEvent(e);
    }
}

class InputPanel extends Panels {
    Font inputFont;
    Color bgColor, inputsColor, equationColor, miscColor;

    double a11, a12, a22, a13, a23, a33;
    TextField[] inputTextFields = new TextField[6];
    String equationString = "";
    TextField equationTextField;
    Button startBtn;

    public InputPanel(GraficConica graficConica) {
        super(graficConica.im);

        this.inputFont = graficConica.mainFont;
        this.bgColor = graficConica.componentColor;
        this.equationColor = graficConica.color1;
        this.inputsColor = graficConica.color2;
        this.miscColor = graficConica.color3;

        for (int i = 0; i < 6; i++) {
            inputTextFields[i] = new TextField();
            inputTextFields[i].setFont(inputFont);
            add(inputTextFields[i]);
        }

        equationTextField = new TextField();
        equationTextField.setFont(inputFont);
        equationTextField.setBackground(miscColor);
        ;
        equationTextField.setEditable(false);
        add(equationTextField);

        startBtn = new Button("Deseneaza");
        startBtn.setBackground(miscColor);
        startBtn.setFont(inputFont);
        startBtn.requestFocus();
        add(startBtn);
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setFont(inputFont);

        g.setColor(bgColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(equationColor);
        g.drawString("Ecuatia conicei: ", 20, 50);
        equationTextField.setBounds(20, 75, 260, 25);
        equationTextField.setText(equationString);

        g.drawString("Introduceti coeficientii ecuatiei: ", 20, 160);

        g.setColor(inputsColor);
        String[] labels = { "a11", "a12", "a22", "a13", "a23", "a33" };
        for (int i = 0; i < 6; i++) {
            inputTextFields[i].setBounds(100, 180 + 50 * i, 80, 25);
            g.drawString(labels[i] + ": ", 50, 200 + 50 * i);
        }

        startBtn.setBounds(90, 550, 100, 30);
    }

    public void getCoefFromInputs() {
        String a11Str = inputTextFields[0].getText();
        a11 = a11Str.isEmpty() ? 0 : Double.parseDouble(a11Str);

        String a12Str = inputTextFields[1].getText();
        a12 = a12Str.isEmpty() ? 0 : Double.parseDouble(a12Str);

        String a22Str = inputTextFields[2].getText();
        a22 = a22Str.isEmpty() ? 0 : Double.parseDouble(a22Str);

        String a13Str = inputTextFields[3].getText();
        a13 = a13Str.isEmpty() ? 0 : Double.parseDouble(a13Str);

        String a23Str = inputTextFields[4].getText();
        a23 = a23Str.isEmpty() ? 0 : Double.parseDouble(a23Str);

        String a33Str = inputTextFields[5].getText();
        a33 = a33Str.isEmpty() ? 0 : Double.parseDouble(a33Str);
    }

    public void setEquation() {
        equationString = "";

        if (a11 != 0) {
            if (a11 == -1) {
                equationString += "-";
            }
            if (a11 != 1 && a11 != -1) {
                if (a11 == (long) a11) {
                    equationString += (long) a11;
                } else {
                    equationString += a11;
                }
            }

            equationString += "x\u00B2";
        }

        if (a12 != 0) {
            if (2 * a12 > 0)
                equationString += "+";

            if (2 * a12 == -1)
                equationString += "-";

            if (2 * a12 != 1 && 2 * a12 != -1) {
                if (2 * a12 == (long) (2 * a12)) {
                    equationString += (long) (2 * a12);
                } else {
                    equationString += 2 * a12;
                }
            }

            equationString += "xy";
        }

        if (a22 != 0) {
            if (a22 > 0)
                equationString += "+";

            if (a22 == -1)
                equationString += "-";

            if (a22 != 1 && a22 != -1) {
                if (a22 == (long) a22) {
                    equationString += (long) a22;
                } else {
                    equationString += a22;
                }
            }

            equationString += "y\u00B2";
        }

        if (a13 != 0) {
            if (2 * a13 > 0)
                equationString += "+";

            if (2 * a13 == -1)
                equationString += "-";

            if (2 * a13 != 1 && 2 * a13 != -1) {
                if (2 * a13 == (long) (2 * a13)) {
                    equationString += (long) (2 * a13);
                } else {
                    equationString += 2 * a13;
                }
            }

            equationString += "x";
        }

        if (a23 != 0) {
            if (2 * a23 > 0)
                equationString += "+";

            if (2 * a23 == -1)
                equationString += "-";

            if (2 * a23 != 1 && 2 * a23 != -1) {
                if (2 * a23 == (long) (2 * a23)) {
                    equationString += (long) (2 * a23);
                } else {
                    equationString += 2 * a23;
                }
            }

            equationString += "y";
        }

        if (a33 != 0) {
            if (a33 > 0)
                equationString += "+";

            if (a33 == (long) a33) {
                equationString += (long) a33;
            } else {
                equationString += a33;
            }
        }

        if (a11 != 0 || a12 != 0 || a22 != 0 || a13 != 0 || a23 != 0 || a33 != 0) {
            equationString += "=0";
        }

        repaint();
    }
}

class PropPanel extends Panels {
    double a11, a12, a22, a13, a23, a33;
    Font propFont, headerFont;
    Color bgColor, propColor, headerColor;
    GraficConica graficConica;
    String gen, tip, nume;

    double Tr_d, d, D, D1;
    double xC, yC;

    public PropPanel(GraficConica graficConica) {
        super(graficConica.im);

        this.graficConica = graficConica;
        this.headerFont = graficConica.headerFont;
        this.propFont = graficConica.mainFont;
        this.bgColor = graficConica.componentColor;
        this.headerColor = graficConica.color1;
        this.propColor = graficConica.color2;
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setFont(headerFont);

        g.setColor(bgColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(headerColor);
        g.drawString("PROPRIETATI", 75, 25);


        g.setFont(propFont);
        
        g.setColor(headerColor);
        g.drawString("Invarianti ortogonali", 20, 50);
        g.setColor(propColor);
        g.drawString("Urma minor ordin 2: " + Tr_d, 20, 75);
        g.drawString("Det minor ordin 2: " + d, 20, 100);
        g.drawString("Det minor ordin 3: " + D, 20, 125);

        g.setColor(headerColor);
        g.drawString("Invariant centro-ortogonal", 20, 175);
        g.setColor(propColor);
        g.drawString("Suma minori ordin 2: " + D1, 20, 200);

        g.setColor(headerColor);
        g.drawString("Clasificare conica", 20, 250);
        g.setColor(propColor);
        g.drawString("Gen: " + gen, 20, 275);
        g.drawString("Tip: " + tip, 20, 300);
        g.drawString("Denumire: " + nume, 20, 325);

        g.setColor(headerColor);
        g.drawString("Centru conica", 20, 375);
        g.setColor(propColor);
        g.drawString("X: " + xC, 20, 400);
        g.drawString("Y: " + yC, 20, 425);
    }

    void updateProp() {
        a11 = graficConica.inputPanel.a11;
        a12 = graficConica.inputPanel.a12;
        a22 = graficConica.inputPanel.a22;
        a13 = graficConica.inputPanel.a13;
        a23 = graficConica.inputPanel.a23;
        a33 = graficConica.inputPanel.a33;

        determineProp();

        repaint();
    }

    public void determineProp() {
        double a21 = a12;
        double a31 = a13;

        Tr_d = a11 + a22;

        d = a11 * a22 - a12 * a21;

        D = a11 * a22 * a33 + a21 * a23 * a13 + a12 * a23 * a13
                - a13 * a22 * a31 - a23 * a23 * a11 - a21 * a12 * a33;

        D1 = (a11*a22 - a12*a21) + (a11*a33 - a13*a31) + (a22*a33 - a23*a23);



        if(d>0){
            gen = "eliptic";

            if(D!=0){
                tip = "nedegenerat";

                if(Tr_d*D < 0){
                    nume="elipsa";
                }
                else if(Tr_d*D > 0){
                    nume="elispa imaginara";
                }
            }
            else{
                tip="degenerat";
                nume="punct dublu";
            }    
        }
        else if(d<0){
            gen = "hiperbolic";

            if(D!=0){
                tip = "nedegenerat";
                nume= "hiperbola";
            }
            else{
                tip="degenerat";
                nume="drepte concurente";
            }  
        }
        else{
            gen = "parabolic";
            if(D!=0){
                tip = "nedegenerat";
                nume= "parabola";
            }
            else{
                tip="degenerat";

                if(D1<0){  
                    nume="drepte paralele";
                }
                else if(D1>0){
                    nume="drepte imaginare paralele";
                }
                else{
                    nume="dreapta dubla";
                } 
            } 
        }
        
        if(d!=0){
            xC = (a12*a23 - a22*a13)/d;
            yC = (a12*a13 - a11*a23)/d; 
        }  
    }
}

class GraphicPanel extends Panels {
    Font propFont, headerFont;
    Color headerColor, headerBgColor, bgColor, axisColor, conicCenterColor, conicSymAxisColor, conicColor;
    GraficConica graficConica;
    double a11, a12, a22, a13, a23, a33;
    double scaleMultiplier;

    public GraphicPanel(GraficConica graficConica) {
        super(graficConica.im);
        this.graficConica = graficConica;

        this.propFont = graficConica.mainFont;
        this.headerFont = graficConica.headerFont;

        this.headerColor = graficConica.color1;
        this.headerBgColor = graficConica.bgColor;
        this.bgColor = graficConica.color3;
        this.axisColor = graficConica.graphicColor1;
        this.conicCenterColor = graficConica.graphicColor2;
        this.conicSymAxisColor = graficConica.graphicColor2;
        this.conicColor = graficConica.graphicColor3;
    }

    public void paint(Graphics g0) {
        super.paint(g0);

        Graphics2D g = (Graphics2D) g0;

        g.setColor(bgColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(axisColor);
        g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
        g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());

        if (a11 != 0 || a12 != 0 || a22 != 0 || a13 != 0 || a23 != 0) {
            int ww = this.getWidth();
            int hh = this.getHeight();
            int translaterX = (int)(ww / 2 + graficConica.propPanel.xC);
            int translaterY = (int)(hh / 2 + graficConica.propPanel.yC);


            g.setColor(conicCenterColor);
            g.fillOval(translaterX-5, translaterY-5, 10, 10);

            g.setColor(conicSymAxisColor);
            g.drawLine(0, translaterY, this.getWidth(), translaterY);
            g.drawLine(translaterX, 0, translaterX, this.getHeight());


            scaleMultiplier = (1 + Math.abs(a33) + Math.max(Math.max(Math.abs(a12), Math.abs(a13)), Math.abs(a23)));

            a11 /= scaleMultiplier;
            a12 /= scaleMultiplier;
            a22 /= scaleMultiplier;
            a13 /= scaleMultiplier;
            a23 /= scaleMultiplier;
            a33 *= scaleMultiplier;

            g.setColor(conicColor);
            for (int i = -ww; i <= ww; i++) {
                for (int j = -hh; j <= hh; j++) {
                    if (isPointOnGraphic(j, i)) {
                        g.drawOval(j + translaterX, -i + translaterY, 1, 1);
                    }
                }
            }
        }

        g.setFont(headerFont);
        g.setColor(headerBgColor);
        g.fillRect((int) (this.getWidth() / 2.35) - 10, 0, 200, 35);
        g.setColor(headerColor);
        g.drawString("GRAFIC CONICA", (int) (this.getWidth() / 2.35), 25);
    }

    boolean isPointOnGraphic(double x, double y) {
        
        double eq = a11 * x * x + 2 * a12 * x * y + a22 * y * y + 2 * a13 * x + 2 * a23 * y + a33;

        double aprox = scaleMultiplier;
        return eq >= -aprox && eq <= aprox;

    }

    void updateGraphic() {
        a11 = graficConica.inputPanel.a11;
        a12 = graficConica.inputPanel.a12;
        a22 = graficConica.inputPanel.a22;
        a13 = graficConica.inputPanel.a13;
        a23 = graficConica.inputPanel.a23;
        a33 = graficConica.inputPanel.a33;

        repaint();
    }
}

class Panels extends Panel {
    public Image im, im1;

    public Panels(Image im) {
        this.im = im;
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        super.paint(g);

        Dimension dimension = size();
        im1 = createImage(dimension.width, dimension.height);
        pan(im1.getGraphics());
        g.drawImage(im1, 0, 0, this);
    }

    public void pan(Graphics g) {
        Dimension dimension = size();
        int w = dimension.width;
        int h = dimension.height;
        Color color = getBackground();
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        for (int k = 0; k < w; k += im.getWidth(this)) {
            for (int l = 0; l < h; l += im.getHeight(this)) {
                g.drawImage(im, k, l, this);
            }
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));

        g2d.setColor(color.darker());
        g2d.drawRect(1, 1, w - 2, h - 2);
    }
}
