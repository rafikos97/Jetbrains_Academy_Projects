package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {
    private JTextField fileNameField;
    private JTextArea textArea;
    private final List<MatchResult> matches = new ArrayList<>();
    private int currentIndex;
    private JFileChooser fileChooser;
    private boolean useRegex;


    public TextEditor() {
        super("Text Editor");
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        ImageIcon loadIcon = new ImageIcon("src/load.png");
        ImageIcon saveIcon = new ImageIcon("src/save.jpg");
        ImageIcon searchIcon = new ImageIcon("src/lupa.png");
        ImageIcon nextIcon = new ImageIcon("src/next.png");
        ImageIcon backIcon = new ImageIcon("src/back.png");

        JButton load = new JButton(loadIcon);
        load.setName("OpenButton");
        load.setPreferredSize(new Dimension(20, 20));

        JButton save = new JButton(saveIcon);
        save.setName("SaveButton");
        save.setPreferredSize(new Dimension(20, 20));

        JButton search = new JButton(searchIcon);
        search.setName("StartSearchButton");
        search.setPreferredSize(new Dimension(20, 20));

        JButton next = new JButton(nextIcon);
        next.setName("NextMatchButton");
        next.setPreferredSize(new Dimension(20, 20));

        JButton back = new JButton(backIcon);
        back.setName("PreviousMatchButton");
        back.setPreferredSize(new Dimension(20, 20));

        JCheckBox regexCheckBox = new JCheckBox("Use regex");
        regexCheckBox.setName("UseRegExCheckbox");

        fileNameField = new JTextField(20);
        fileNameField.setName("SearchField");
        fileNameField.setPreferredSize(new Dimension(150, save.getPreferredSize().height));

        textArea = new JTextArea(16,38);
        textArea.setName("TextArea");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        menuBar.add(fileMenu);
        menuBar.add(searchMenu);

        JMenuItem loadMenuItem = new JMenuItem("Open");
        loadMenuItem.setName("MenuOpen");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        JMenuItem startSearchItem = new JMenuItem("Start search");
        startSearchItem.setName("MenuStartSearch");
        JMenuItem previousSearchItem = new JMenuItem("Previous search");
        previousSearchItem.setName("MenuPreviousMatch");
        JMenuItem nextSearchItem = new JMenuItem("Next match");
        nextSearchItem.setName("MenuNextMatch");
        JMenuItem useRegExItem = new JMenuItem("Use regular expressions");
        useRegExItem.setName("MenuUseRegExp");

        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        searchMenu.add(startSearchItem);
        searchMenu.add(previousSearchItem);
        searchMenu.add(nextSearchItem);
        searchMenu.add(useRegExItem);

        JPanel panel = new JPanel();
        setLayout(new FlowLayout());
        panel.add(load);
        panel.add(save);
        panel.add(fileNameField);
        panel.add(search);
        panel.add(back);
        panel.add(next);
        panel.add(regexCheckBox);

        add(panel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        setLayout(new FlowLayout());
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPanel.add(scrollableTextArea);
        add(textPanel, BorderLayout.CENTER);

        fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setName("FileChooser");
        this.add(fileChooser);

        loadMenuItem.addActionListener(e -> loadFile());

        saveMenuItem.addActionListener(e -> saveFile());

        exitMenuItem.addActionListener(e -> dispose());

        load.addActionListener(e -> loadFile());

        save.addActionListener(e -> saveFile());

        regexCheckBox.addActionListener(e -> useRegex = true);

        search.addActionListener(e -> {
            Thread thread = new Thread(this::search);
            thread.start();
        });

        next.addActionListener(e -> next());

        back.addActionListener(e -> previous());

        startSearchItem.addActionListener(e -> {
            Thread thread = new Thread(this::search);
            thread.start();
        });

        previousSearchItem.addActionListener(e -> previous());

        nextSearchItem.addActionListener(e -> next());

        useRegExItem.addActionListener(e -> regexCheckBox.doClick());
    }

    private void search() {
        matches.clear();
        String text = textArea.getText();
        String wordToFind = fileNameField.getText().trim();
        Pattern pattern = Pattern.compile(wordToFind);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            matches.add(matcher.toMatchResult());
        }

        if (!matches.isEmpty()) {
            currentIndex = 0;
            int index = matches.get(currentIndex).start();
            String foundText = matches.get(currentIndex).group();
            textArea.setCaretPosition(index + foundText.length());
            textArea.select(index, index + foundText.length());
            textArea.grabFocus();
        }
    }

    private void next() {
        if (currentIndex + 1 < matches.size()) {
            currentIndex++;
        } else if (currentIndex == matches.size() - 1) {
            currentIndex = 0;
        }

        int index = matches.get(currentIndex).start();
        String foundText = matches.get(currentIndex).group();
        textArea.setCaretPosition(index + foundText.length());
        textArea.select(index, index + foundText.length());
        textArea.grabFocus();
    }

    private void previous() {
        if (currentIndex - 1 < 0) {
            currentIndex = matches.size() - 1;
        } else {
            currentIndex--;
        }

        int index = matches.get(currentIndex).start();
        String foundText = matches.get(currentIndex).group();
        textArea.setCaretPosition(index + foundText.length());
        textArea.select(index, index + foundText.length());
        textArea.grabFocus();
    }

    private void loadFile() {
        File selectedFile = null;
        textArea.setText(null);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }

        if (selectedFile != null) {
            try {
                FileReader reader = new FileReader(selectedFile);
                BufferedReader br = new BufferedReader(reader);
                textArea.read(br, null);
                br.close();
                textArea.requestFocus();
            }
            catch(Exception e2) {
                System.err.println("Error occurred");
                e2.printStackTrace();
            }
        }
    }

    private void saveFile() {
        File selectedFile = null;

        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }

        if (selectedFile != null) {
            try {
                PrintWriter out = new PrintWriter(new FileWriter(selectedFile));
                textArea.write(out);
                out.close();
            } catch (IOException e2) {
                System.err.println("Error occurred");
                e2.printStackTrace();
            }
        }
    }
}