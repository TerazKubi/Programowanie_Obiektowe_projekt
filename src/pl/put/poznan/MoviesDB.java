package pl.put.poznan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.table.*;

public class MoviesDB extends JFrame {

    private final Repository repository;
    private JPanel mainPanel;
    private JPanel contentContainer;
    private JPanel navBarPanel;

    private JButton switchPanelMovieListButton;
    private JButton switchPanelAddMovieButton;
    private JButton switchPanelAddRentButton;
    private JButton switchPanelRentListButton;

    private JPanel movieListPanel;
    private JPanel addMoviePanel;
    private JPanel rentListPanel;
    private JPanel addRentPanel;

    private JPanel activePanel;

    private JTable movieListTable;
    private JTable rentListTable;

    private JTextField movieTitleTextField;
    private JTextField premiereYearTextField;
    private JTextField diskCountTextField;
    private JTextField clientNameTextField;
    private JTextField phoneNumberTextField;

    private JComboBox diskTypeComboBox;
    private JComboBox rentMovieComboBox;

    private JLabel errorLabel;
    private JLabel successLabel;
    private JLabel bestClientLabel;
    private JLabel bestMovieLabel;

    private JButton addMovieButton;
    private JButton deleteMovieButton;
    private JButton goToAddMoviePanelButton;
    private JButton goToAddRentPanelButton;
    private JButton returnMovieButton;
    private JButton addRentButton;


    private final DefaultTableModel movieModel = new DefaultTableModel();
    private final DefaultTableModel rentModel = new DefaultTableModel();
    private final DefaultComboBoxModel diskTypeModel = new DefaultComboBoxModel();
    private final DefaultComboBoxModel rentMovieComboBoxModel = new DefaultComboBoxModel();


    private Map<Integer, Disk> disks = new HashMap<>();
    private int idCount = 0;

    private Map<Integer, Rent> rents = new HashMap<>();
    private int idRentCount = 0;


    private String bestClientName="";
    private String bestMovie="";
    private int max=0;



    public MoviesDB(Repository repository){
        this.repository = repository;
        setupFrame();
        setupModels();
        setupActionListeners();

        read();

        updateRentMovieComboBoxModel();

        updateBestClient();
        updateBestMovie();
    }

    private void setupModels(){
        //setup table and model for MovieList table
        movieListTable.setModel(movieModel);
        movieModel.setColumnCount(5);

        String movieColumnNames[] ={"ID", "Tytul", "Premiera","Rodzaj", "Ilosc"};
        Integer movieColumnWidths[] = {30, 0, 70, 50, 70};

        for(int i=0; i<5;i++){
            TableColumn col = movieListTable.getColumnModel().getColumn(i);
            col.setHeaderValue(movieColumnNames[i]);
            if(i == 1) continue;
            col.setMaxWidth(movieColumnWidths[i]);
        }
        //setup table and model for RentList table
        rentListTable.setModel(rentModel);
        rentModel.setColumnCount(5);

        String rentColumnNames[] ={"ID", "Imię i nazwisko", "Nr tel.","Film", "Stan"};
        Integer rentColumnWidths[] = {30, 0, 70, 0, 50};
        for(int i=0; i<5;i++){
            TableColumn col =rentListTable.getColumnModel().getColumn(i);
            col.setHeaderValue(rentColumnNames[i]);
            if(i == 1 || i == 3) continue;
            col.setMaxWidth(rentColumnWidths[i]);
        }

        //setup comboboxes //listy rozwijane
        diskTypeComboBox.setModel(diskTypeModel);
        ArrayList<String> types = new ArrayList<>();
        types.add("CD");
        types.add("DVD");
        types.add("Blu-ray");
        diskTypeModel.addAll(types);

        rentMovieComboBox.setModel(rentMovieComboBoxModel);
    }
    private void setupActionListeners(){
        switchPanelMovieListButton.addActionListener(e -> switchActivePanel(movieListPanel));
        switchPanelAddMovieButton.addActionListener(e -> switchActivePanel(addMoviePanel));
        switchPanelRentListButton.addActionListener(e->switchActivePanel(rentListPanel));
        switchPanelAddRentButton.addActionListener(e->switchActivePanel(addRentPanel));
        goToAddMoviePanelButton.addActionListener(e -> switchActivePanel(addMoviePanel));
        goToAddRentPanelButton.addActionListener(e -> switchActivePanel(addRentPanel));

        addMovieButton.addActionListener(e -> addMovieToTable());
        addRentButton.addActionListener(e -> addRentToTable());

        deleteMovieButton.addActionListener(e->deleteMovieFromTable());
        returnMovieButton.addActionListener(e->returnMovie());

        movieTitleTextField.addActionListener(e-> premiereYearTextField.grabFocus());
        premiereYearTextField.addActionListener(e-> diskCountTextField.grabFocus());
        diskCountTextField.addActionListener(e->{
            diskTypeComboBox.grabFocus();
            diskTypeComboBox.setPopupVisible(true);
        });
        diskTypeComboBox.addKeyListener(new KeyListenerComboBox() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(diskTypeComboBox.isPopupVisible()){
                        diskTypeComboBox.setPopupVisible(false);
                    }else{
                        addMovieToTable();
                    }
                }
            }
        });

        clientNameTextField.addActionListener(e->phoneNumberTextField.grabFocus());
        phoneNumberTextField.addActionListener(e->{
            rentMovieComboBox.grabFocus();
            rentMovieComboBox.setPopupVisible(true);
        });
        rentMovieComboBox.addKeyListener(new KeyListenerComboBox() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(rentMovieComboBox.isPopupVisible()){
                        rentMovieComboBox.setPopupVisible(false);
                    }else{
                        addRentToTable();
                    }
                }
            }
        });
    }
    private void setupFrame(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 500));
        setMaximumSize(new Dimension(700, 600));
        setBounds(100, 100, 600, 500);

        setVisible(true);
        setTitle("Wypożyczalnia filmów");

        addMoviePanel.setVisible(false);
        rentListPanel.setVisible(false);
        addRentPanel.setVisible(false);
        this.activePanel = movieListPanel;

    }

    private void addMovieToTable(){
        String title = null;
        Integer premiereYear = null;
        Integer count = null;
        String type = null;

        try {
            title = Validators.getMovieTitle(movieTitleTextField.getText());
            premiereYear = Validators.getMoviePremiereYear((premiereYearTextField.getText()));
            type = Validators.getDiskType((String) diskTypeComboBox.getSelectedItem());
            count = Validators.getDiskCount(diskCountTextField.getText());
        }
        catch(MoviesDBExceptions e){
            errorLabelSetMessage(e.toString());
        }
        if (title != null && premiereYear != null && count != null && type != null){

            Disk disk = new Disk(this.idCount, title, premiereYear, count, count, type);
            disks.put(this.idCount, disk);

            this.idCount ++;

            updateMovieModel();

            movieTitleTextField.setText("");
            premiereYearTextField.setText("");
            diskCountTextField.setText("");
            diskTypeComboBox.setSelectedIndex(-1);

            updateRentMovieComboBoxModel();
            switchActivePanel(movieListPanel);
            successLabelSetMessage("Pomyślnie dodano film");

            save();
        }

    }
    private void deleteMovieFromTable(){
        Integer index = movieListTable.getSelectedRow();
        if(index==-1)return;

        Disk d = disks.get(movieModel.getValueAt(index, 0));

        if(d.getDiskCountNow() != d.getDiskCountMax()){
            errorLabelSetMessage("Nie można usunać tego filmu.");
        }else{
            int movieID = d.getID();
            Iterator<Map.Entry<Integer, Rent>> iterator = rents.entrySet().iterator();

            while(iterator.hasNext()){
                Map.Entry<Integer, Rent> entry = iterator.next();
                if(entry.getValue().getMovieID() == movieID){
                    iterator.remove();
                }
            }
            disks.remove(d.getID());
            updateMovieModel();
            updateRentModel();

            updateRentMovieComboBoxModel();

            save();
            updateBestClient();
            updateBestMovie();
            successLabelSetMessage("Pomyślnie usunięto film");
        }
    }
    private void addRentToTable(){
        String name = null;
        String phoneNumber = null;
        Disk movieDisk = null; //movieDisk to rent

        try{
            name = Validators.getName(clientNameTextField.getText());
            phoneNumber = Validators.getPhoneNumber(phoneNumberTextField.getText());
            movieDisk = Validators.getDisk((Disk) rentMovieComboBox.getSelectedItem());
        }catch(MoviesDBExceptions e){
            errorLabelSetMessage(e.toString());
        }
        if(name != null && phoneNumber != null && movieDisk != null){
            Rent r = new Rent(this.idRentCount, name, phoneNumber, movieDisk.getID(), "WYPOZ");
            rents.put(r.getID(), r);

            this.idRentCount++;

            movieDisk.rentDisk();
            updateRentModel();
            updateMovieModel();

            updateRentMovieComboBoxModel();
            switchActivePanel(rentListPanel);
            successLabelSetMessage("Pomyślnie wypożyczono płytę");

            clientNameTextField.setText("");
            phoneNumberTextField.setText("");
            rentMovieComboBox.setSelectedIndex(-1);

            save();
            updateBestMovie();
            updateBestClient();
        }
    }
    private void returnMovie(){
        Integer i = rentListTable.getSelectedRow();
        if(i==-1)return;
        String s = (String)rentModel.getValueAt(i, 4);
        if(s.equals("WYPOZ")){
            Rent rent = rents.get(rentModel.getValueAt(i, 0));
            Disk d = disks.get(rent.getMovieID());
            d.returnDisk();
            rent.changeStatus();

            updateRentMovieComboBoxModel();
            updateMovieModel();
            updateRentModel();
        }
        successLabelSetMessage("Pomyślnie zwrócono płytę");
        save();
    }

    private void switchActivePanel(JPanel panel){
        if(activePanel != panel){
            activePanel.setVisible(false);
            activePanel = panel;
            activePanel.setVisible(true);

            if (activePanel == addMoviePanel){
                movieTitleTextField.grabFocus();
            }else if(activePanel == addRentPanel){
                clientNameTextField.grabFocus();
            }

            errorLabelSetMessage("");
            successLabelSetMessage("");
        }
    }
    private void errorLabelSetMessage(String txt){
        errorLabel.setText(txt);
    }
    private void successLabelSetMessage(String txt){
        successLabel.setText(txt);
    }
    private String getMovieTitleByID(int ID){
        for(Map.Entry<Integer, Disk> pair : disks.entrySet()){
            if(pair.getKey() == ID){
                return pair.getValue().getTitle();
            }
        }
        return "";
    }

    private void save(){
        ArrayList<DiskEntity> disksEntities = new ArrayList<>();
        ArrayList<Rent> rentEntities = new ArrayList<>();
        IdsEntity ids = new IdsEntity(this.idCount, this.idRentCount);
        disks.forEach((k, v) ->{
            disksEntities.add(v.getAsEntity());
        });
        rents.forEach((k, v) ->{
            rentEntities.add(v);
        });
        try {
            repository.saveDisksToFile(disksEntities);
            repository.saveIdsToFile(ids);
            repository.saveRentsToFile(rentEntities);
        } catch (CannotSaveToFileException e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }
    private void read(){
        ArrayList<DiskEntity> disksEntities = repository.readDisksFromFile();
        ArrayList<Rent> rentsEntities = repository.readRentsFromFile();
        IdsEntity ids = repository.readIdsFromFile();
        disksEntities.forEach(e ->{
            disks.put(e.getID(), e.getAsDisk());
        });
        rentsEntities.forEach(e ->{
            rents.put(e.getID(), e);
        });
        this.idCount = ids.getMovieIDCount();
        this.idRentCount = ids.getRentIDCount();

        updateMovieModel();
        updateRentModel();
    }


    private void updateMovieModel(){
        if(movieModel.getRowCount() > 0){
            for(int i=movieModel.getRowCount() -1; i > -1;i--){
                movieModel.removeRow(i);
            }
        }
        disks.forEach((k,v)->{
            movieModel.addRow(v.getAsEntity().getAsRow());
        });
    }
    private void updateRentModel(){
        if(rentModel.getRowCount() > 0){
            for(int i=rentModel.getRowCount() -1; i > -1;i--){
                rentModel.removeRow(i);
            }
        }
        rents.forEach((k,v)->{
            rentModel.addRow(v.getAsRow(getMovieTitleByID(v.getMovieID())));
        });
    }
    private void updateBestClient(){
        if(rents.size() <= 0) return;

        max=0;
        Map<String, Integer> clients = new HashMap<String, Integer>();
        rents.forEach((k, v) -> {
            String name = v.getName();
            if(clients.containsKey(name)){
                int oldValue = clients.get(name);
                clients.replace(name, oldValue+1);
            }else{
                clients.put(name, 1);
            }
        });
        clients.forEach((k, v) ->{
            if(v > max){
                bestClientName = k;
                max = v;
            }
        });
        if(!bestClientName.equals("")){
            bestClientLabel.setText(bestClientName);
        }else{
            bestClientLabel.setText("");
        }

    }
    private void updateBestMovie(){
        if(rents.size() <= 0) return;

        max=0;
        Map<String, Integer> rentedMovies = new HashMap<String, Integer>();
        rents.forEach((k, v) -> {
            String movieID = Integer.toString(v.getMovieID());
            if(rentedMovies.containsKey(movieID)){
                int oldValue = rentedMovies.get(movieID);
                rentedMovies.replace(movieID, oldValue+1);
            }else{
                rentedMovies.put(movieID, 1);
            }
        });
        rentedMovies.forEach((k, v) ->{
            if(v > max){
                bestMovie = k;
                max = v;
            }
        });
        if(!bestMovie.equals("")){
            bestMovieLabel.setText(disks.get(Integer.valueOf(bestMovie)).getTitle());
        }

    }
    private void updateRentMovieComboBoxModel(){
        rentMovieComboBoxModel.removeAllElements();
        ArrayList<Disk> moviesIdToRent = new ArrayList<>();
        for (Map.Entry<Integer, Disk> entry : disks.entrySet()){
            Disk p = entry.getValue();
            if (p.getDiskCountNow() > 0){
                moviesIdToRent.add(p);
            }
        }
        rentMovieComboBoxModel.addAll(moviesIdToRent);
    }

}
