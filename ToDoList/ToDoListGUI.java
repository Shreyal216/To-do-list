import javax.swing.*;                                                 //JFrame, JList, JScrollPane, JTextField, JButton, JPanel, GridLayout, BorderLayout, DefaultListModel
import java.awt.*;                                                    //ActionEvent, ActionListener, Container, FlowLayout
import java.awt.event.*;                                              //ActionEvent, ActionListener
import java.io.*;                                                     //BufferedReader, BufferedWriter, FileReader, FileWriter, IOException
import java.util.*;      

public class ToDoListGUI {                                            //ToDoListGUI class creates a simple to-do list GUI.
    private DefaultListModel<String> taskListModel;                    //DefaultListModel<String> stores tasks dynamically.
    private JList<String> taskList;                                   //JList<String> displays tasks.
    private JTextField taskInput;                                     //JTextField for user input.
    private static final String FILE_NAME = "tasks.txt";               //File name to store tasks.

    public ToDoListGUI() {
        //  main frame
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());                         //BorderLayout organizes elements into NORTH, CENTER, SOUTH.

        // Create list model and load tasks
        taskListModel = new DefaultListModel<>();                    //DefaultListModel<String> stores tasks dynamically.
        loadTasks();
        taskList = new JList<>(taskListModel);                       //JList<String> displays tasks.
        JScrollPane scrollPane = new JScrollPane(taskList);          //JScrollPane enables scrolling.


        // Create input field and buttons
        taskInput = new JTextField();                               //JTextField for user input.
        JButton addButton = new JButton("Add");                //JButton for adding tasks.
        JButton removeButton = new JButton("Remove");          //JButton for removing tasks.
        JButton clearButton = new JButton("Clear All");        //JButton for clearing all tasks.

        // Handling button clicks 
        addButton.addActionListener(e -> addTask());       //
        removeButton.addActionListener(e -> removeTask()); //        Listens for button clicks and calls respective methods
        clearButton.addActionListener(e -> clearTasks());  //

        // Layout panel for buttons
        JPanel panel = new JPanel();                                       //JPanel organizes components in a grid layout.
        panel.setLayout(new GridLayout(1, 3, 5, 5));   //GridLayout(rows, columns, horizontal gap, vertical gap).
        panel.add(addButton);                                              //addButton is added to the panel.
        panel.add(removeButton);                                           //removeButton is added to the panel.
        panel.add(clearButton);                                            //clearButton is added to the panel.

        // Add components to frame
        frame.add(taskInput, BorderLayout.NORTH);                          //taskInput is added to the top of the frame.
        frame.add(scrollPane, BorderLayout.CENTER);                        //scrollPane is added to the center of the frame.
        frame.add(panel, BorderLayout.SOUTH);                              //panel is added to the bottom of the frame.

        // Show GUI
        frame.setVisible(true);                                         //frame.setVisible(true); is necessary to make the GUI window appear. 
    }

    // Add task to list
    private void addTask() {                                              //addTask() method adds a task to the list.
    String task = taskInput.getText().trim();                             //taskInput.getText() gets the text from the input field.
        if (!task.isEmpty()) {                                            //isEmpty() checks if the task is empty.
            taskListModel.addElement(task);                               //addElement(task) adds the task to the list model.
            taskInput.setText("");                                      //setText("") clears the input field.
            saveTasks();                                                  //saveTasks() saves the tasks to a file.
        }
    }

    // Remove selected task
    private void removeTask() {                                           //removeTask() method removes a task from the list.
        int selectedIndex = taskList.getSelectedIndex();                  //getSelectedIndex() gets the index of the selected task.
        if (selectedIndex != -1) {                                        //If a task is selected, remove it.
            taskListModel.remove(selectedIndex);                          //remove(selectedIndex) removes the task from the list model.
            saveTasks();                                                  //saveTasks() saves the tasks to a file.
        }
    }

    // Clear all tasks
    private void clearTasks() {                                            //clearTasks() method clears all tasks from the list.
        taskListModel.clear();                                             //clear() removes all tasks from the list model.
        saveTasks();                     
    }

    // Load tasks from file
    private void loadTasks() {                                                          //loadTasks() method loads tasks from a file.
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {   //BufferedReader reads tasks from a file.
            String task;                                                                //FileReader reads the file.
            while ((task = reader.readLine()) != null) {                                //readLine() reads a line from the file.
                taskListModel.addElement(task);                                         //addElement(task) adds the task to the list model.
            }
        } catch (IOException e) {                                                       //IOException handles file reading errors.
            System.out.println("No previous tasks found.");                           //Prints a message if no tasks are found.
        }
    }

    // Save tasks to file
    private void saveTasks() {                                                          
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {    //BufferedWriter writes tasks to a file.
            for (int i = 0; i < taskListModel.size(); i++) {                             //size() gets the number of tasks in the list model.
                writer.write(taskListModel.getElementAt(i));                             //getElementAt(i) gets the task at index i.
                writer.newLine();                                                        //newLine() writes a new line.
            }
        } catch (IOException e) {                                                         // catch (IOException e) handles file writing errors.
            System.out.println("Error saving tasks.");                                  //Prints a message if an error occurs.
        }
    }

    public static void main(String[] args) {                                             //main() method creates an instance of ToDoListGU
    SwingUtilities.invokeLater(ToDoListGUI::new);                                        //I and displays the GUI.
    }
}
