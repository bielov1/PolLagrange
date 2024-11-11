import java.awt.*;
import javax.swing.*;

public class Display extends JPanel {

    public static JTextField nTextField = new JTextField();
    public static JTextField divTextField = new JTextField();
    public static JTextField periodTextField1 = new JTextField();
    public static JTextField periodTextField2 = new JTextField();
    public static JTextField yResultTextField2 = new JTextField();
    public static JTextField accuracyTextField2 = new JTextField();
    public static JTextField ROPTextField2 = new JTextField();



    public Display(){
        setLayout(null);

        //-----------------------TextFields---------------------------
        layout(nTextField, 140, 185, 70, 20);
        layout(divTextField, 110, 245, 100, 20);
        layout(periodTextField1, 100, 295, 40, 20);
        layout(periodTextField2, 160, 295, 40, 20);
        yResultTextField2.setEnabled(false);
        layout(yResultTextField2, 310, 185, 170, 20);
        accuracyTextField2.setEnabled(false);
        layout(accuracyTextField2, 340, 245, 140, 20);
        ROPTextField2.setEnabled(false);
        layout(ROPTextField2, 440, 295, 130, 20);

        //------------------------Labels---------------------------
        JLabel topic = new JLabel("Інтерполяція функцій");
        topic.setFont(new Font("New Times Roman", Font.PLAIN, 25));
        topic.setForeground(Color.red);
        layout(topic, 190, 0, 300, 50);


        JLabel myVar = new JLabel("Обчислення функції f(x) = sin(x^2) на проміжку [0,2]" +
                                       " за допомогою Інтерполяційного многочлена Лагранжа");
        myVar.setFont(new Font("New Times Roman", Font.PLAIN, 12));
        layout(myVar, 12, 25, 620, 50);

        JLabel inLabel = new JLabel("Вхідні дані");
        layout(inLabel, 80, 90, 100, 50);

        JLabel resultLabel = new JLabel("Результати");
        layout(resultLabel, 365, 90, 100, 50);

        JLabel nLabel = new JLabel("Кількість вузлів: ");
        nLabel.setForeground(Color.white);
        layout(nLabel, 30, 170, 150, 50);

        JLabel divLabel = new JLabel("Значення x:");
        divLabel.setForeground(Color.white);
        layout(divLabel, 30, 230, 100, 50);

        JLabel periodLabel = new JLabel("Проміжок:");
        periodLabel.setForeground(Color.white);
        layout(periodLabel, 30, 280, 100, 50);

        JLabel semicolon = new JLabel(";");
        semicolon.setForeground(Color.WHITE);
        layout(semicolon, 150, 300, 40, 20);

        JLabel yResultLabel = new JLabel("y = ");
        yResultLabel.setForeground(Color.white);
        layout(yResultLabel, 280, 185, 70, 20);

        JLabel accuracyLabel = new JLabel("Похибка");
        accuracyLabel.setForeground(Color.white);
        layout(accuracyLabel, 280, 245, 70, 20);

        //розмитість оцінки похибки
        JLabel ROPLabel = new JLabel("Розмитість оцінки похибки");
        ROPLabel.setForeground(Color.white);
        layout(ROPLabel, 280, 295, 170, 20);



        //-----------------------Panels---------------------------
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        inputPanel.setBackground(Color.GRAY);
        layout(inputPanel, 20, 125, 200, 250);


        JPanel resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        resultPanel.setBackground(Color.GRAY);
        layout(resultPanel, 270, 125, 300, 250);

        //-----------------------Buttons---------------------------
        JButton calcButton = new JButton("Обчислити");
        calcButton.addActionListener(e -> BodyApp.fillTheArr());
        layout(calcButton, 30, 500, 125, 40);

        JButton saveButton = new JButton("Зберегти дані");
        saveButton.addActionListener(e -> BodyApp.saveData());
        layout(saveButton, 200, 500, 125, 40);

        JButton experimentButton = new JButton("Чисельний експеримент");
        experimentButton.addActionListener(e -> BodyApp.experiment());
        layout(experimentButton, 330, 500, 175, 40);

    }


    public void layout(Component component, int x, int y,
                       int width, int height){
        component.setLocation(x, y);
        component.setSize(width, height);
        add(component);
    }
}
