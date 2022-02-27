import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import oracle.jdbc.driver.*;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Movie extends JFrame{
		static Connection con;
		static Statement stmt;
		
		JLabel l1, l2, l3, l4, l5, l6, l7, l8;
		JTextField t1, t2, t3, t4, t5, t6, t7;
		JButton b1;
		Movie(){
			Font f=new Font("Arial", Font.BOLD, 24);
			l1 = new JLabel("Movie Search System"); 
			l1.setFont(f);
			l2 = new JLabel("Movie ID");
			t1 = new JTextField();
			l3 = new JLabel("Title");
			t2 = new JTextField();
			l4 = new JLabel("Language");
			t3 = new JTextField();
			l5 = new JLabel("Production Company");
			t4 = new JTextField();
			l6 = new JLabel("Production Country");
			t5 = new JTextField();
			l7 = new JLabel("Release date");
			t6 = new JTextField();
			l8 = new JLabel("Runtime");
			t7 = new JTextField();
			b1 = new JButton("Search"); 
			l1.setBounds(100,40,300,40);  //left spot, top spot, width, height
			l2.setBounds(100,90, 100, 20); 
			t1.setBounds(250,85,300,30);
			l3.setBounds(100,140, 100, 20); 
			t2.setBounds(250,135,300,30);
			l4.setBounds(100,190, 100, 20); 
			t3.setBounds(250,185,300,30);
			l5.setBounds(100,240, 200, 20); 
			t4.setBounds(250,235,300,30); 
			l6.setBounds(100,290, 200, 20); 
			t5.setBounds(250,285,300,30);
			l7.setBounds(100,340, 100, 20); 
			t6.setBounds(250,335,300,30);
			l8.setBounds(100,390, 100, 20); 
			t7.setBounds(250,385,300,30);
			b1.setBounds(100, 450, 100, 30); 
			add(l1); //add to Jframe
			add(l2); 
			add(t1); 
			add(l3);
			add(t2);
			add(l4); 
			add(t3); 
			add(l5);
			add(t4);
			add(l6); 
			add(t5); 
			add(l7);
			add(t6);
			add(l8);
			add(t7);
			add(b1); 
			setLayout(null); //layout of jframe class
			setVisible(true);
			setSize(400,400);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes on clicking red cross
			b1.addActionListener(new ActionListener() { //when clicking button
				public void actionPerformed(ActionEvent ae){
					if(ae.getSource() == b1){
					//sets column names
					String[] columnNames = {"Title", "Language", "Production Company", "Production Country", "Release Date", "Runtime", "Movie ID", "Average Rating"};
						//set table model
						DefaultTableModel model = new DefaultTableModel();
						//create table 
						JTable table = new JTable();
						table.setModel(model); 
						model.setColumnIdentifiers(columnNames); //add column names to table
						Container cnt = getContentPane();  //Container for table component
						cnt.setLayout(null); 
						
						
					int count = 0;  //count for number of clauses
					try {
	
						
						String sql = "select Title, Language, Production_company, Production_country, Release_date, Runtime, MovieID, AVG(Rating) from Movies INNER JOIN Ratings USING(movieID)";
						String query = "";
						//movieid
						if(!(t1.getText().equals(""))){
							
							query = getquery(count); 
							count++;
							sql = sql.concat(query); 
							sql = sql.concat(" MovieID = " + t1.getText());
						}
						//title
						if(!(t2.getText().equals(""))){
							query = getquery(count); 
							count++;
							sql = sql.concat(query);
							sql = sql.concat(" lower(Title) like '%" + t2.getText().toLowerCase() + "%' ");
							//JOptionPane.showMessageDialog(null, t2.getText());
						}
						//language
						if(!(t3.getText().equals(""))){
							query = getquery(count); 
							count++;
							sql = sql.concat(query);
							sql = sql.concat(" Language = '" + t3.getText().toLowerCase() + "' ");
							//JOptionPane.showMessageDialog(null, t2.getText());
						}
						//production_company
						if(!(t4.getText().equals(""))){
							query = getquery(count); 
							count++;
							sql = sql.concat(query);
							sql = sql.concat(" lower(Production_company) like '%" + t4.getText().toLowerCase() + "%' ");
							//JOptionPane.showMessageDialog(null, t2.getText());
						}
						//production_country
						if(!(t5.getText().equals(""))){
							query = getquery(count); 
							count++;
							sql = sql.concat(query);
							sql = sql.concat(" lower(Production_country) like '%" + t5.getText().toLowerCase() + "%' ");
							//JOptionPane.showMessageDialog(null, t2.getText());
						}
						//release_date format dd-MMM-yyyy
						if(!(t6.getText().equals(""))){
							query = getquery(count); 
							count++;
							sql = sql.concat(query);
							String datePattern = "\\d{1,2}-\\d{1,2}-\\d{4}";
							String datePattern1 = "\\d{1,2}/\\d{1,2}/\\d{4}";
							String datePattern2 = "\\d{4}-\\d{1,2}-\\d{1,2}";
							String myDateString = "";
							if(t6.getText().matches(datePattern)){
							SimpleDateFormat newDateFormat = new SimpleDateFormat("MM-dd-yyyy");
							Date myDate = newDateFormat.parse(t6.getText());
							newDateFormat.applyPattern("dd-MMM-yyyy");
							myDateString = newDateFormat.format(myDate);
							}
							else if(t6.getText().matches(datePattern1)){
								SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
								Date myDate = newDateFormat.parse(t6.getText());
								newDateFormat.applyPattern("dd-MMM-yyyy");
								myDateString = newDateFormat.format(myDate);
							}
							else if(t6.getText().matches(datePattern2)){
								SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
								Date myDate = newDateFormat.parse(t6.getText());
								newDateFormat.applyPattern("dd-MMM-yyyy");
								myDateString = newDateFormat.format(myDate);
							}
							else{
								myDateString = t6.getText();
							}
							sql = sql.concat(" Release_date = '" + myDateString + "' ");
							
						}
						//runtime
						if(!(t7.getText().equals(""))){
							query = getquery(count); 
							count++;
							sql = sql.concat(query);
							sql = sql.concat(" Runtime = '" + t7.getText() + "' ");
							
						}
						sql = sql.concat(" GROUP BY Title, Language, Production_company, Production_country, Release_date, Runtime, MovieID"); 
						PreparedStatement ps = con.prepareStatement(sql);
						ResultSet rs = ps.executeQuery();
						String title = "";
						String language = "";
						String pcom = "";
						String pcou = "";
						String release = "";
						String runtime = "";
						String mid = "";
						String avg = "";
						
						//add values to table
						add(table);
						while(rs.next()){
							title= rs.getString("title"); 
							language = rs.getString("language");
							pcom = rs.getString("production_company");
							pcou = rs.getString("production_country");
							release = rs.getString("release_date");
							runtime = rs.getString("runtime");
							mid = rs.getString("movieID");
							avg = rs.getString("avg(rating)"); 
							model.addRow(new Object[]{title,language, pcom, pcou, release, runtime, mid, avg});
						
							
							
						} 
					
					}catch(Exception e) {System.out.println(e);};
					
					JScrollPane pg = new JScrollPane(table);
					pg.setBounds(600,80,600,500); //set size of table 
					add(pg); 
					
					}		
				}
			});
		}
	public static void main(String[] args) {
		connectToDatabase();
		Movie movie = new Movie();
	}
	public static String getquery(int count){
		String query = ""; 
		if(count > 0){
			query = " and";
		}
		else {
			query = " where";
		}
		return query; 
	}
			
	public static void connectToDatabase()
    {
	String driverPrefixURL="jdbc:oracle:thin:@";
        String username=null;
        String password=null;
        String dataSource=null;
        try{
	    //Register Oracle driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (Exception e) {
        System.out.println("Failed to load JDBC/ODBC driver.");
        return;
     }
       try{
	   //look for resource file 'odbc.datasource'
          InputStream is=ClassLoader.getSystemResourceAsStream("odbc.datasource");
Properties p=new Properties();
p.load(is);
dataSource=p.getProperty("datasource.name");
if (dataSource==null) throw new Exception();
 username=p.getProperty("datasource.username","");
 password=p.getProperty("datasource.password","");
}
 catch (Exception e){
   System.out.println("Unable to read resouce file to get data source");
return;
}

try{
System.out.println(driverPrefixURL+dataSource);
con=DriverManager.getConnection(driverPrefixURL+dataSource, username, password);
DatabaseMetaData dbmd=con.getMetaData();
stmt=con.createStatement();

System.out.println("Connected.");

if(dbmd==null){
  System.out.println("No database meta data");
}

else {
   System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
   System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
   System.out.println("Database Driver Name: "+dbmd.getDriverName());
   System.out.println("Database Driver Version: "+dbmd.getDriverVersion());
}
}catch( Exception e) {e.printStackTrace();}

    } //end of connect to database 

}
	