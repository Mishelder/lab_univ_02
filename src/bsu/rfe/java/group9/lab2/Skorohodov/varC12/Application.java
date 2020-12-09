package bsu.rfe.java.group9.lab2.Skorohodov.varC12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.*;

public class Application extends JFrame {

    private static final int HEIGHT = 350;
    private static final int WIDTH = 600;
    private static boolean formula = true;
    private static final List<JPanel> panels = new ArrayList<>(10);
    private static final ButtonGroup radioButtonsForm = new ButtonGroup();
    private static final ButtonGroup radioButtonsVal = new ButtonGroup();
    private static double variable1;
    private static double variable2;
    private static double variable3;
    private static int varId = 1;
    private static final JTextField varText1 = new JTextField("0.0", 10);
    private static final JTextField varText2 = new JTextField("0.0", 10);
    private static final JTextField varText3 = new JTextField("0.0", 10);
    private static final JTextField resultText = new JTextField("0.0", 30);
    private static final Application application = new Application();

    private Application(){}

    public static void start(){
        application.initPanel();
        firstPanel();
        secondPanel();
        thirdPanel();
        fourthPanel();
        fifthPanel();
        sixthPanel();
        application.initFrame();
    }

    private double calculate1(double x, double y, double z) {
        return sin(log10(y) + sin(PI * y * y)) *
                pow(x * x + sin(z) + exp(cos(z)), 0.25);
    }

    private double calculate2(double x, double y, double z) {
        return pow((cos(exp(x)) + log10(1 + y) * log10(1 + y) +
                sqrt(exp((cos(x))) + sin(PI * z) * sin(PI * z) +
                        sqrt(1 / z) + cos(y) * cos(y))), sin(z));
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(new Dimension(WIDTH, HEIGHT));
        setContentPane(mainPanel);
    }

    private void createPanel(JPanel panel, LayoutManager lm, int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        if (lm != null)
            panel.setLayout(lm);
        panels.add(panel);
        add(panel);
    }

    private void createRadioButton(JPanel panel, String name, ButtonGroup buttonGroup, ActionListener a) {
        JRadioButton radioButton = new JRadioButton(name, true);
        radioButton.addActionListener(a);
        buttonGroup.add(radioButton);
        panel.add(radioButton);
    }

    private void createButton(JPanel panel, String name, ActionListener a) {
        JButton button = new JButton(name);
        button.addActionListener(a);
        panel.add(button);
    }

    private Image getImage(String name, String format) {
        String fileName = "src/" + name + "." + format;
        return new ImageIcon(fileName).getImage();
    }

    private static void sixthPanel() {
        application.createPanel(new JPanel(), new FlowLayout(), 550, 50);
        application.createButton(panels.get(5), "Вычислить", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double result;
                try {
                    variable1 = Double.parseDouble(varText1.getText());
                    variable2 = Double.parseDouble(varText2.getText());
                    variable3 = Double.parseDouble(varText3.getText());
                } catch (NumberFormatException ex) {
                    //NOP
                }
                if (formula) {
                    result = application.calculate1(variable1, variable2, variable3);
                } else {
                    result = application.calculate2(variable1, variable2, variable3);
                }
                resultText.setText(result + "");
            }
        });
        application.createButton(panels.get(5), "Очистить поля", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                variable1 = 0;
                variable2 = 0;
                variable3 = 0;
                resultText.setText("0.0");
                varText1.setText("0.0");
                varText2.setText("0.0");
                varText3.setText("0.0");
            }
        });
    }

    private static void fifthPanel() {
        application.createPanel(new JPanel(), new FlowLayout(), 550, 50);
        application.createButton(panels.get(4), "MC", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (varId == 1) {
                    variable1 = 0;
                    varText1.setText("0.0");
                } else if (varId == 2) {
                    variable2 = 0;
                    varText2.setText("0.0");
                } else if (varId == 3) {
                    variable3 = 0;
                    varText3.setText("0.0");
                } else
                    throw new IllegalArgumentException("MC Exception :: variable not found");

            }
        });
        application.createButton(panels.get(4), "M+", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (varId == 1) {
                    variable1 += Double.parseDouble(resultText.getText());
                    varText1.setText(variable1 + "");
                } else if (varId == 2) {
                    variable2 += Double.parseDouble(resultText.getText());
                    varText2.setText(variable2 + "");
                } else if (varId == 3) {
                    variable3 += Double.parseDouble(resultText.getText());
                    varText3.setText(variable3 + "");
                } else
                    throw new IllegalArgumentException("M+ Exception :: variable not found");

            }
        });
    }

    private static void fourthPanel() {
        application.createPanel(new JPanel(), new FlowLayout(), 550, 50);
        panels.get(3).add(new JLabel("Результат"));
        panels.get(3).add(resultText);
    }

    private static void thirdPanel() {
        String var1 = "Пер 1";
        String var2 = "Пер 2";
        String var3 = "Пер 3";
        application.createPanel(new JPanel(), new FlowLayout(), 550, 40);
        application.createRadioButton(panels.get(2), var1, radioButtonsVal, (a) -> varId = 1);
        panels.get(2).add(varText1);
        application.createRadioButton(panels.get(2), var2, radioButtonsVal, (a) -> varId = 2);
        panels.get(2).add(varText2);
        application.createRadioButton(panels.get(2), var3, radioButtonsVal, (a) -> varId = 3);
        panels.get(2).add(varText3);
    }

    private static void secondPanel() {
        String formula1 = "Формула 1";
        String formula2 = "Формула 2";
        application.createPanel(new JPanel(), null, 370, 30);
        application.createRadioButton(panels.get(1), formula1,
                radioButtonsForm, (a) -> {
                    formula = true;
                    panels.get(0).repaint();
                });
        application.createRadioButton(panels.get(1), formula2,
                radioButtonsForm, (a) -> {
                    formula = false;
                    panels.get(0).repaint();
                });
        panels.get(1).getComponent(0).setEnabled(true);
    }

    private static void firstPanel() {
        application.createPanel(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (formula)
                    g.drawImage(application.getImage("img1", "jpg"), 0, 0, this);
                else
                    g.drawImage(application.getImage("img2", "jpg"), 0, 0, this);
            }
        }, null, 570, 50);
    }
}
