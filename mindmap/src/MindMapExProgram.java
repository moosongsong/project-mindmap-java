import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MindMapExProgram extends JFrame {
    private static int sizeX = 1500;
    private static int sizeY = 900;
    private static int sizeMiniX1 = 250;
    private static int sizeMiniX2 = 900;
    private Container contentPane;
    private static int labelSize = 50;

    public MindMapExProgram() {
        setTitle("Mind Map Program"); //프로그램 이름
        contentPane = getContentPane();
        setSize(sizeX, getSizeY());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenu();    // 메뉴바 생성
        createToolbar();    // 툴바 생성

        JSplitPane center = new JSplitPane();
        JSplitPane centerright = new JSplitPane();
        center.setDividerLocation(getSizeMiniX1());  // Text Editor Pane 폭
        centerright.setDividerLocation(getSizeMiniX2()); //Mind Map pane 폭

        centerright.setRightComponent(new AttributePane());
        centerright.setLeftComponent(new CenterMindMapPane());
        center.setRightComponent(centerright);
        center.setLeftComponent(new TextEditorPane()); //좌측 TextEditorPane 삽입

        contentPane.add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createMenu() {  //Menu 생성
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("파일");
        JMenu saveMenu = new JMenu("저장");
        JMenu programMenu = new JMenu("프로그램");
        mb.setBackground(new Color(255, 178, 217));

        JMenuItem[] menuItem = new JMenuItem[7];
        String[] itemTitle = {"새로만들기", "열기", "저장", "다른 이름으로 저장", "적용", "변경", "닫기"};

        for (int i = 0; i < menuItem.length; i++) {
            menuItem[i] = new JMenuItem(itemTitle[i]);
            menuItem[i].addActionListener(new MenuAndToolBarActionListner());
        }

        menuItem[4].addActionListener(new ApplyButtonActionListener());
        menuItem[5].addActionListener(new AttributeActionListener());

        fileMenu.add(menuItem[0]);
        fileMenu.add(menuItem[1]);
        mb.add(fileMenu);

        saveMenu.add(menuItem[2]);
        saveMenu.add(menuItem[3]);
        mb.add(saveMenu);

        programMenu.add(menuItem[4]);
        programMenu.add(menuItem[5]);
        programMenu.add(menuItem[6]);
        mb.add(programMenu);

        setJMenuBar(mb);
    }

    private void createToolbar() {  //toolbar생성
        JToolBar toolbar = new JToolBar("Mind Map Menu");
        toolbar.setBackground(new Color(255, 217, 236));

        JButton[] toolItem = new JButton[7];
        String[] toolItemTitle = {"New", "Open", "Save", "Save Diffrent Name", "Apply", "Change", "Close"};
        String[] toolTip = {"파일을 생성합니다.", "파일을 가져옵니다.", "파일을 저장합니다.", "파일을 다른이름으로 저장합니다.", "텍스트 편집내용을 마인드 맵에 적용합니다.", "변경 내용을 마인드 맵에 적용합니다.", "프로그램을 닫습니다."};

        for (int i = 0; i < toolItem.length; i++) {
            toolItem[i] = new JButton(toolItemTitle[i]);
            toolItem[i].setBackground(Color.WHITE);
            toolbar.add(toolItem[i]);
            toolItem[i].addActionListener(new MenuAndToolBarActionListner());
            toolItem[i].setToolTipText(toolTip[i]);
        }
        toolItem[4].addActionListener(new ApplyButtonActionListener());
        toolItem[5].addActionListener(new AttributeActionListener());

        ToolTipManager m = ToolTipManager.sharedInstance();
        m.setInitialDelay(0);
        m.setDismissDelay(3000);

        contentPane.add(toolbar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {  //main
        new MindMapExProgram();
    }

    public static int getLabelSize() {
        return labelSize;
    }

    public void setLabelSize(int labelSize) {
        this.labelSize = labelSize;
    }

    public static int getSizeMiniX2() {
        return sizeMiniX2;
    }

    public static void setSizeMiniX2(int sizeMiniX2) {
        MindMapExProgram.sizeMiniX2 = sizeMiniX2;
    }

    public static int getSizeY() {
        return sizeY;
    }

    public static void setSizeY(int sizeY) {
        MindMapExProgram.sizeY = sizeY;
    }

    public static int getSizeMiniX1() {
        return sizeMiniX1;
    }

    public static void setSizeMiniX1(int sizeMiniX1) {
        MindMapExProgram.sizeMiniX1 = sizeMiniX1;
    }
}

/************************************PANEL**********************************************/
class TextEditorPane extends JPanel {
    private JPanel back1, back2;
    private JLabel namelabel;
    static JTextArea textfield;
    private JButton textbutton;

    TextEditorPane() {
        this.setLayout(new BorderLayout());

        namelabel = new JLabel("Text Editor Pane");
        back1 = new JPanel();
        back1.add(namelabel);
        add(back1, BorderLayout.NORTH);

        textfield = new JTextArea("", 20, 20);
        textfield.setTabSize(2);

        add(new JScrollPane(textfield), BorderLayout.CENTER);

        textbutton = new JButton("적용");
        back2 = new JPanel();
        back2.add(textbutton);
        add(back2, BorderLayout.SOUTH);

        textbutton.addActionListener(new ApplyButtonActionListener());
    }

    public static void resetTextEditorPane() {
        textfield.setText("");
    }
}

class MyTree {
    static int totalnum;
    static int totalHeight;
    static int NumOfGrade[];
    int num;

    private MyTree nextgrade;
    private MyTree next;
    private MyTree parent;
    private String name;
    private int grade;

    public MyTree() {
        setNextgrade(null);
        next = null;
        this.name = "root";
        this.grade = -1;
    }

    public MyTree(int grade, String name) {
        this.grade = grade;
        this.name = name;

        setNextgrade(null);
        next = null;
    }

    public void setNext(MyTree obj) {
        next = obj;
    }

    public void setNextGrade(MyTree obj) {
        setNextgrade(obj);
    }

    public void setParent(MyTree obj) {
        parent = obj;
    }

    public MyTree getNext() {
        return next;
    }

    public MyTree getNextGrade() {
        return getNextgrade();
    }

    public MyTree getParent() {
        return parent;
    }

    public String getName() {
        return this.name;
    }

    public int getGrade() {
        return this.grade;
    }

    public void travel() {
        MyTree test1 = this.getNextGrade();

        while (test1 != this) {
            while (test1.getNextGrade() != null) {
//				System.out.println(test1.getName() +"," + test1.getGrade());
                if (test1.grade != -1) {
                    MyTree.NumOfGrade[test1.grade]++;
                }
                test1 = test1.getNextGrade();
            }
            int flag = 0;
            while (flag == 0) {
                test1 = test1.getParent();
                if (test1 == this) {
                    break;
                }
                if (test1.getNext() != null) {
                    if (test1.next.grade != -1) {
                        test1 = test1.getNext();
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                } else {
                    ;
                }
            }
        }
    }

    public MyTree getNextgrade() {
        return nextgrade;
    }

    public void setNextgrade(MyTree nextgrade) {
        this.nextgrade = nextgrade;
    }
}

class CenterMindMapPane extends JPanel {
    public static MindMapPanel mindmapfiled = new MindMapPanel();
    static boolean textmodyflag1 = false;
    static JLabel[] treeElement;
    static MyTree[] findparentelm;
    static int letsCount[];
    static boolean miniflag = true;

    public CenterMindMapPane() {
        this.setLayout(new BorderLayout());
        JLabel namelabel = new JLabel("Mind Map Pane");
        namelabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(namelabel, BorderLayout.NORTH);
        mindmapfiled.setBackground(Color.WHITE);
        add(new JScrollPane(mindmapfiled), BorderLayout.CENTER);

    }

    public static void deleteLabel() {
        mindmapfiled.removeAll();
        CenterMindMapPane.mindmapfiled.add(new JLabel("."));
        CenterMindMapPane.mindmapfiled.repaint();
    }

    public static void showLabel(MyTree tree) {
        mindmapfiled.removeAll();
        treeElement = new JLabel[MyTree.totalnum];
        findparentelm = new MyTree[MyTree.totalnum];
        letsCount = new int[MyTree.totalHeight + 2];

        if (miniflag == true) {
            for (int i = 0; i < letsCount.length; i++) {
                letsCount[i] = 0;
            }
            miniflag = false;
        }

        MyTree test1 = tree.getNextGrade();
        LabelMouseListener listener = new LabelMouseListener();

        int i = 0;
        while (test1 != tree) {
            while (test1.getNextGrade() != null) {
                findparentelm[i] = test1;
                findparentelm[i].num = i;
                treeElement[i] = new JLabel(test1.getName());
                treeElement[i].setOpaque(true);
                treeElement[i].setBackground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
                treeElement[i].setBounds(computeX(test1), computeY(test1), MindMapExProgram.getLabelSize(), MindMapExProgram.getLabelSize());
                treeElement[i].setHorizontalAlignment(SwingConstants.CENTER);
                treeElement[i].addMouseListener(listener);
                treeElement[i].addMouseMotionListener(new LabelDragListener());
                treeElement[i].setForeground(Color.white);
                CenterMindMapPane.mindmapfiled.add(treeElement[i]);
                CenterMindMapPane.mindmapfiled.repaint();
                i++;
                test1 = test1.getNextGrade();
            }
            int flag = 0;
            while (flag == 0) {
                test1 = test1.getParent();
                if (test1 == tree) {
                    break;
                }
                if (test1.getNext() != null) {
                    if (test1.getNext().getGrade() != -1) {
                        test1 = test1.getNext();
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                } else {
                    ;
                }
            }
        }
    }

    public static void changeLabel() {
        LabelMouseListener listener = new LabelMouseListener();
        CenterMindMapPane.textmodyflag1 = true;
        mindmapfiled.removeAll();
        for (int i = 0; i < MyTree.totalnum; i++) {
            treeElement[i].setOpaque(true);
            treeElement[i].addMouseListener(listener);
            CenterMindMapPane.mindmapfiled.add(treeElement[i]);
            CenterMindMapPane.mindmapfiled.repaint();
        }
    }

    static int step, count = 0;

    static int computeX(MyTree tree) {
        step = (MindMapExProgram.getSizeMiniX2() - MindMapExProgram.getLabelSize() / 2) / (MyTree.NumOfGrade[tree.getGrade()] + 1);
        count = tree.getGrade();
        letsCount[count] = letsCount[count] + 1;
        return letsCount[count] * step;
    }

    static int computeY(MyTree tree) {
        int step;
        step = (MindMapExProgram.getSizeY() - MindMapExProgram.getLabelSize() / 2) / (MyTree.totalHeight + 3);
        return (tree.getGrade() + 1) * step;
    }
}

class MindMapPanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0, xx;

        for (int i = 0; i < MyTree.totalnum; i++) {

            if (CenterMindMapPane.findparentelm[i].getGrade() == 0) {
            } else {
                x1 = CenterMindMapPane.treeElement[i].getX() + CenterMindMapPane.treeElement[i].getWidth() / 2;
                y1 = CenterMindMapPane.treeElement[i].getY() + CenterMindMapPane.treeElement[i].getHeight() / 2;
                xx = CenterMindMapPane.findparentelm[i].getParent().num;
                x2 = CenterMindMapPane.treeElement[xx].getX() + CenterMindMapPane.treeElement[xx].getWidth() / 2;
                y2 = CenterMindMapPane.treeElement[xx].getY() + CenterMindMapPane.treeElement[xx].getHeight() / 2;

            }
            g.setColor(Color.black);
            g.drawLine(x1, y1, x2, y2);
        }

    }


    public void resetGraphics() {
        repaint();
        remove(0);
    }
}

class AttributePane extends JPanel {
    private JPanel back1, back2;
    private JLabel namelabel, text, x, y, w, h, color;
    static JTextField text_value, x_value, y_value, w_value, h_value, color_value;
    private JButton changebutton;

    AttributePane() {
        this.setLayout(new BorderLayout());
        namelabel = new JLabel("Attribute Pane");
        back1 = new JPanel();
        back1.add(namelabel);
        add(back1, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(6, 2, 5, 5));

        text = new JLabel("TEXT: ");
        x = new JLabel("X:	");
        y = new JLabel("Y:	");
        w = new JLabel("W:	");
        h = new JLabel("H:	");
        color = new JLabel("Color: ");
        text.setHorizontalAlignment(0);
        x.setHorizontalAlignment(0);
        y.setHorizontalAlignment(0);
        w.setHorizontalAlignment(0);
        h.setHorizontalAlignment(0);
        color.setHorizontalAlignment(0);

        text_value = new JTextField(20);
        x_value = new JTextField(10);
        y_value = new JTextField(10);
        w_value = new JTextField(10);
        h_value = new JTextField(10);
        color_value = new JTextField(10);

        center.add(text);
        center.add(text_value);
        center.add(x);
        center.add(x_value);
        center.add(y);
        center.add(y_value);
        center.add(w);
        center.add(w_value);
        center.add(h);
        center.add(h_value);
        center.add(color);
        center.add(color_value);

        add(center, BorderLayout.CENTER);

        changebutton = new JButton("변경");
        back2 = new JPanel();
        back2.add(changebutton);
        add(back2, BorderLayout.SOUTH);

        changebutton.addActionListener(new AttributeActionListener());
    }

    public static void resetAttributePane() {
        AttributePane.text_value.setText(null);
        AttributePane.w_value.setText(null);
        AttributePane.h_value.setText(null);
        AttributePane.x_value.setText(null);
        AttributePane.y_value.setText(null);
        AttributePane.color_value.setText(null);
    }
}

/*****************************************LISTENER************************************************/

class MenuAndToolBarActionListner implements ActionListener {
    private JFileChooser chooser;

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "닫기":
            case "Close":
                int result = JOptionPane.showConfirmDialog(null, "닫으시겠습니까?", "프로그램닫기", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                    break;
                } else {
                    break;
                }
            case "열기":
            case "Open":
                chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("*.txt", ".txt"));
                int ret = chooser.showOpenDialog(null);
                if (ret != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                    break;
                } else if (ret == JFileChooser.APPROVE_OPTION) {
                    String filePath = chooser.getSelectedFile().getPath();
                    try {
                        File file = chooser.getSelectedFile();
                        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                        String s = "";
                        int k = 0;
                        while ((s = br.readLine()) != null) {
                            TextEditorPane.textfield.append(s);
                            TextEditorPane.textfield.append("\n");
                        }
                        if (br != null)
                            br.close();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }
                }
                break;
            case "새로만들기":
            case "New":
                int result2 = JOptionPane.showConfirmDialog(null, "파일을 새로 만드시겠습니까?", "새로 만들기", JOptionPane.YES_NO_OPTION);
                if (result2 == JOptionPane.YES_OPTION) {

                    TextEditorPane.textfield.setText(".");
                    ApplyButtonActionListener.actionPerformed(true);
                    TextEditorPane.resetTextEditorPane();
                    CenterMindMapPane.mindmapfiled.removeAll();
                    CenterMindMapPane.mindmapfiled.repaint();

                    AttributePane.resetAttributePane();
                    break;
                } else {
                    break;
                }
            case "저장":
            case "Save":
                chooser = new JFileChooser();
                FileNameExtensionFilter filter2 = new FileNameExtensionFilter("*.txt", ".txt");

                chooser.setFileFilter(filter2);
                int ret2 = chooser.showSaveDialog(null);
                if (ret2 != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null, "파일을 저장하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                    break;
                } else if (ret2 == JFileChooser.APPROVE_OPTION) {
                    File fi = chooser.getSelectedFile();

                    try {
                        BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(fi + ".txt"));

                        String text = TextEditorPane.textfield.getText();
                        String testline[] = text.split("\n");

                        for (int i = 0; i < testline.length; i++) {
                            bufferwrite.write(testline[i] + "\r\n");
                        }

                        bufferwrite.close();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }
                    break;
                }
                break;
            case "다른 이름으로 저장":
            case "Save Diffrent Name":
                chooser = new JFileChooser();
                FileNameExtensionFilter filter3 = new FileNameExtensionFilter("*.txt", ".txt");

                chooser.setFileFilter(filter3);
                int ret3 = chooser.showSaveDialog(null);
                if (ret3 != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null, "파일을 저장하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                    break;
                } else if (ret3 == JFileChooser.APPROVE_OPTION) {
                    File fi = chooser.getSelectedFile();

                    try {
                        BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(fi + ".txt"));

                        for (int i = 0; i < MyTree.totalnum; i++) {
                            bufferwrite.write(CenterMindMapPane.treeElement[i].getText() + "\r\n");
                            bufferwrite.write(CenterMindMapPane.treeElement[i].getX() + "\r\n");
                            bufferwrite.write(CenterMindMapPane.treeElement[i].getY() + "\r\n");
                            bufferwrite.write(CenterMindMapPane.treeElement[i].getWidth() + "\r\n");
                            bufferwrite.write(CenterMindMapPane.treeElement[i].getHeight() + "\r\n");
                            Color labelcolor = CenterMindMapPane.treeElement[i].getBackground();
                            String r1 = Integer.toHexString(labelcolor.getRed());
                            String g1 = Integer.toHexString(labelcolor.getGreen());
                            String b1 = Integer.toHexString(labelcolor.getBlue());
                            bufferwrite.write(r1 + "" + g1 + "" + b1 + "\r\n");
                        }

                        bufferwrite.close();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }
                    break;
                }
                break;
        }
    }
}

class ApplyButtonActionListener implements ActionListener {
    static int grade;
    static MyTree root_point;
    static boolean textAreamodyflag1 = false;

    public void actionPerformed(ActionEvent e) {
        try {
            textAreamodyflag1 = true;
            JButton b = (JButton) e.getSource();
            MyTree root = new MyTree();
            root_point = root;

            String text = TextEditorPane.textfield.getText();
            String t[] = text.split("\n");

            for (int i = 0; i < t.length; i++) {
                int j = 0;
                grade = 0;
                while (t[i].charAt(j) == '\t') {
                    grade++;
                    j++;
                }
                t[i] = t[i].trim();

                if (MyTree.totalHeight < grade) {
                    MyTree.totalHeight = grade;
                }

                if (root_point.getGrade() < grade) {
                    root_point.setNextGrade(new MyTree(grade, t[i]));
                    root_point.getNextgrade().setParent(root_point);
                    root_point = root_point.getNextGrade();
                    root_point.setNextGrade(new MyTree());    //dummy node
                    root_point.getNextgrade().setParent(root_point);
                    root_point.setNext(new MyTree());    //dummy node
                } else if (root_point.getGrade() == grade) {
                    root_point.setNext(new MyTree(grade, t[i]));
                    root_point.getNext().setParent(root_point.getParent());
                    root_point = root_point.getNext();
                    root_point.setNext(new MyTree());    //dummy node
                    root_point.setNextGrade(new MyTree());    //dummy node
                    root_point.getNextgrade().setParent(root_point);
                } else {
                    root_point = root;

                    for (int k = grade; k >= 0; k--) {
                        root_point = root_point.getNextGrade();
                    }

                    while (root_point.getNext().getGrade() != -1) {
                        root_point = root_point.getNext();
                    }
                    root_point.setNext(new MyTree(grade, t[i]));
                    root_point.getNext().setParent(root_point.getParent());
                    root_point = root_point.getNext();
                    root_point.setNext(new MyTree());    //dummy node
                    root_point.setNextGrade(new MyTree());    //dummy node
                    root_point.getNextgrade().setParent(root_point);
                }
            }
            MyTree.NumOfGrade = new int[MyTree.totalHeight + 1];
            root.travel();
            MyTree.totalnum = t.length;
            root_point = root;

            CenterMindMapPane.showLabel(root_point);
        } catch (StringIndexOutOfBoundsException e1) {
            JOptionPane.showMessageDialog(null, "TextArea에 단어들을 적은 뒤 적용 버튼을 눌러주세요!", "경고", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void actionPerformed(boolean e) {
        textAreamodyflag1 = true;
        MyTree root = new MyTree();
        root_point = root;

        String text = TextEditorPane.textfield.getText();
        String t[] = text.split("\n");

        for (int i = 0; i < t.length; i++) {
            int j = 0;
            grade = 0;
            while (t[i].charAt(j) == '\t') {
                grade++;
                j++;
            }
            t[i] = t[i].trim();

            if (MyTree.totalHeight < grade) {
                MyTree.totalHeight = grade;
            }

            if (root_point.getGrade() < grade) {
                root_point.setNextGrade(new MyTree(grade, t[i]));
                root_point.getNextgrade().setParent(root_point);
                root_point = root_point.getNextGrade();
                root_point.setNextGrade(new MyTree());    //dummy node
                root_point.getNextgrade().setParent(root_point);
                root_point.setNext(new MyTree());    //dummy node
            } else if (root_point.getGrade() == grade) {
                root_point.setNext(new MyTree(grade, t[i]));
                root_point.getNext().setParent(root_point.getParent());
                root_point = root_point.getNext();
                root_point.setNext(new MyTree());    //dummy node
                root_point.setNextGrade(new MyTree());    //dummy node
                root_point.getNextgrade().setParent(root_point);
            } else {
                root_point = root;

                for (int k = grade; k >= 0; k--) {
                    root_point = root_point.getNextGrade();
                }

                while (root_point.getNext().getGrade() != -1) {
                    root_point = root_point.getNext();
                }
                root_point.setNext(new MyTree(grade, t[i]));
                root_point.getNext().setParent(root_point.getParent());
                root_point = root_point.getNext();
                root_point.setNext(new MyTree());    //dummy node
                root_point.setNextGrade(new MyTree());    //dummy node
                root_point.getNextgrade().setParent(root_point);
            }
        }
        MyTree.NumOfGrade = new int[MyTree.totalHeight + 1];
        root.travel();
        MyTree.totalnum = t.length;
        root_point = root;

        CenterMindMapPane.showLabel(root_point);

        e = false;
    }
}

class LabelMouseListener implements MouseListener {
    int rgb, r, g, b;
    String cc, r1, g1, b1;
    static JLabel point;

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel lb = (JLabel) e.getSource();
        point = lb;
        AttributePane.text_value.setText(lb.getText());
        AttributePane.text_value.setEditable(false);
        AttributePane.w_value.setText(Integer.toString(lb.getWidth()));
        AttributePane.h_value.setText(Integer.toString(lb.getHeight()));
        AttributePane.x_value.setText(Integer.toString(lb.getX()));
        AttributePane.y_value.setText(Integer.toString(lb.getY()));
        cc = Integer.toHexString(rgb);
        r1 = Integer.toHexString(r);
        g1 = Integer.toHexString(g);
        b1 = Integer.toHexString(b);
        AttributePane.color_value.setText(r1 + "" + g1 + "" + b1);
    }

    public void mouseEntered(MouseEvent e) {
        JLabel lb = (JLabel) e.getSource();
        Color color = lb.getBackground();
        rgb = color.getRGB();
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
    }

    public void mousePressed(MouseEvent e) {
        JLabel lb = (JLabel) e.getSource();
        Color color = new Color(213, 213, 213);
        lb.setBackground(color);
    }

    public void mouseReleased(MouseEvent e) {
        JLabel lb = (JLabel) e.getSource();
        Color color = new Color(r, g, b);
        lb.setBackground(color);
        point = lb;
        AttributePane.text_value.setText(lb.getText());
        AttributePane.text_value.setEditable(false);
        AttributePane.w_value.setText(Integer.toString(lb.getWidth()));
        AttributePane.h_value.setText(Integer.toString(lb.getHeight()));
        AttributePane.x_value.setText(Integer.toString(lb.getX()));
        AttributePane.y_value.setText(Integer.toString(lb.getY()));
        r1 = Integer.toHexString(r);
        g1 = Integer.toHexString(g);
        b1 = Integer.toHexString(b);
        AttributePane.color_value.setText(r1 + "" + g1 + "" + b1);
    }

    public void mouseExited(MouseEvent e) {
        JLabel lb = (JLabel) e.getSource();
        Color color = new Color(r, g, b);
        lb.setBackground(color);
    }
}

class LabelDragListener extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent e) {
        int x, y;
        JLabel lb = (JLabel) e.getSource();
        x = e.getXOnScreen() - (MindMapExProgram.getSizeMiniX1() + MindMapExProgram.getLabelSize());
        y = e.getYOnScreen() - 150;
        lb.setLocation(x, y);
    }

    public void mouseExited(MouseEvent e) {
        JLabel lb = (JLabel) e.getSource();
        AttributePane.text_value.setText(lb.getText());
        AttributePane.text_value.setEditable(false);
        AttributePane.w_value.setText(Integer.toString(lb.getWidth()));
        AttributePane.h_value.setText(Integer.toString(lb.getHeight()));
        AttributePane.x_value.setText(Integer.toString(lb.getX()));
        AttributePane.y_value.setText(Integer.toString(lb.getY()));
    }
}

class AttributeActionListener implements ActionListener {
    String change_value;
    int string_to_int;
    int string_to_int2;

    public void actionPerformed(ActionEvent e) {
        JButton change = (JButton) e.getSource();
        try {
            change_value = AttributePane.text_value.getText();

            change_value = AttributePane.x_value.getText();
            string_to_int = Integer.parseInt(change_value);
            change_value = AttributePane.y_value.getText();
            string_to_int2 = Integer.parseInt(change_value);
            LabelMouseListener.point.setLocation(string_to_int, string_to_int2);

            change_value = AttributePane.w_value.getText();
            string_to_int = Integer.parseInt(change_value);
            change_value = AttributePane.h_value.getText();
            string_to_int2 = Integer.parseInt(change_value);
            LabelMouseListener.point.setSize(string_to_int, string_to_int2);

            change_value = AttributePane.color_value.getText();
            string_to_int = Integer.parseInt(change_value, 16);
            Color test = new Color(string_to_int);
            LabelMouseListener.point.setBackground(test);

            CenterMindMapPane.changeLabel();

        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "변경할 Label을 선택한 뒤 변경 버튼을 눌러주세요!", "경고", JOptionPane.WARNING_MESSAGE);
        }
    }
}
