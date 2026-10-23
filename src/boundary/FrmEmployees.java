package boundary;


import java.awt.*;
import javax.swing.*;


import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


import control.EmployeeLogic;
import entity.Consts;
import entity.Employee;
import util.JTextFieldLimiter;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.TitledBorder;

import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.SwingConstants;


public class FrmEmployees extends RootLayout {

	/**
	 *
	 */
	private static final long serialVersionUID = 1;
	private ArrayList<entity.Employee> empArray;
    private Integer currentEmployee;
    private boolean inAddMode;


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method Launch the application.
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmEmployees frame = new FrmEmployees();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method refresh data presented and components
	// according to from state
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public FrmEmployees() {
		initComponents();

		fetchAndRefresh();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method contain all the code for creating and
	// initializing components
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 503);
		updateMenuSelectionEmployee();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmEmployees.class.getResource("/boundary/images/logo.png")));
		setTitle("Employee Details");
		btnNewButton.setBounds(541, 642, 89, 23);
		contentPane.setLayout(null);
		pnlEmpDetails.setBorder(null);
		pnlEmpDetails.setBounds(21, 11, 273, 189);
		contentPane.add(pnlEmpDetails);
		tfEmployeeID = new JTextField();
		tfEmployeeID.setEditable(false);
		tfEmployeeID.setText("123");
		tfEmployeeID.setColumns(10);
		tfLastName = new JTextField();
		tfLastName.setColumns(10);
		tfFirstName = new JTextField();
		tfFirstName.setColumns(10);
		lblTitle = new JLabel("Title:");
		tfTitle = new JTextField();
		tfTitle.setColumns(10);
		contentPane.add(btnNewButton);
		pnlEmpContact.setBounds(330, 11, 235, 189);
		contentPane.add(pnlEmpContact);
		tfHomePhone.setColumns(10);
		tfExtension = new JTextField();
		tfExtension.setColumns(10);
		tfPhoto = new JTextField();
		tfPhoto.setColumns(10);
		tfAddress.setColumns(10);
		tfCity = new JTextField();
		tfCity.setColumns(10);
		pnlAddressDetails.setBounds(21, 211, 309, 104);
		contentPane.add(pnlAddressDetails);
		pnlAddressDetails.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tfCountry = new JTextField();
		tfCountry.setColumns(10);
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 11));


		tfLastName.setDocument(new JTextFieldLimiter(Employee.MAX_LNAME));
        tfFirstName.setDocument(new JTextFieldLimiter(Employee.MAX_FNAME));
        tfTitle.setDocument(new JTextFieldLimiter(Employee.MAX_TITLE));
        tfAddress.setDocument(new JTextFieldLimiter(Employee.MAX_ADDRESS));
        tfCity.setDocument(new JTextFieldLimiter(Employee.MAX_CITY));
        tfCountry.setDocument(new JTextFieldLimiter(Employee.MAX_COUNTRY));
        tfHomePhone.setDocument(new JTextFieldLimiter(Employee.MAX_HOMEPHONE));
        tfExtension.setDocument(new JTextFieldLimiter(Employee.MAX_EXTENSION));
        tfPhoto.setDocument(new JTextFieldLimiter(Employee.MAX_PHOTO));
 		pnlActionBtn = new JPanel();
		pnlActionBtn.setBounds(10, 326, 607, 33);
		tfNavigation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNavigation.setText("showing X of Y");
		tfNavigation.setColumns(6);
		contentPane.add(pnlActionBtn);
		pnlActionBtn.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		btnFirst.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnFirst.setHorizontalAlignment(SwingConstants.LEFT);
		pnlActionBtn.add(btnFirst);
		btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnPrev);
		pnlActionBtn.add(tfNavigation);
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnNext);
		btnLast.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnLast);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 10));
        tfSearch = new JTextField();
        tfSearch.setText("Search");
        pnlActionBtn.add(tfSearch);
        tfSearch.setColumns(10);
        pnlActionBtn.add(btnSave);
        btnSave.setEnabled(inAddMode);
        btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 10));
        pnlActionBtn.add(btnRemove);
        btnRemove.setEnabled(!inAddMode);
        pnlActionBtn.add(btnAdd);
        btnAdd.setEnabled(!inAddMode);


        initGroupLayoutleftPanel();
        initGroupLayoutRightPanel();
        initGroupLayoutAddressPanel();
		createEvents();
		}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method initializing the design structure
	// of address related components
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initGroupLayoutAddressPanel() {
		gl_pnlAddressDetails.setHorizontalGroup(
				gl_pnlAddressDetails.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlAddressDetails.createSequentialGroup()


						.addGroup(gl_pnlAddressDetails.createParallelGroup(Alignment.LEADING)
							.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblAddress_1)
							.addComponent(lblCity)
							.addComponent(lblCountry))



							.addGroup(gl_pnlAddressDetails.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAddress_1)
								.addComponent(tfAddress, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfCity, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfCountry, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
								)


						)
			);
			gl_pnlAddressDetails.setVerticalGroup(
				gl_pnlAddressDetails.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlAddressDetails.createSequentialGroup()
						.addComponent(lblAddress)
						.addPreferredGap(ComponentPlacement.RELATED)

						.addGroup(gl_pnlAddressDetails.createParallelGroup(Alignment.LEADING)


								.addComponent(lblAddress_1)
								.addComponent(tfAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)

									)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlAddressDetails.createParallelGroup(Alignment.LEADING)
						     .addComponent(lblCity)
							.addComponent(tfCity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlAddressDetails.createParallelGroup(Alignment.LEADING)
							.addComponent(lblCountry)
							.addComponent(tfCountry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
			);
			pnlAddressDetails.setLayout(gl_pnlAddressDetails);


	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method initializing the design structure
	// of form right side fields
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initGroupLayoutRightPanel() {
		gl_pnlEmpContact.setHorizontalGroup(
				gl_pnlEmpContact.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlEmpContact.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pnlEmpContact.createParallelGroup(Alignment.LEADING)
							.addComponent(lblHomePhone)
							.addComponent(lblExtension)
							.addComponent(lblPhoto))
						.addGap(10)
						.addGroup(gl_pnlEmpContact.createParallelGroup(Alignment.LEADING, false)
							.addComponent(tfHomePhone, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(tfExtension)
							.addComponent(tfPhoto))
						.addContainerGap())
			);

			gl_pnlEmpContact.setVerticalGroup(
				gl_pnlEmpContact.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlEmpContact.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_pnlEmpContact.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfHomePhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblHomePhone))
						.addPreferredGap(ComponentPlacement.RELATED)

						.addGroup(gl_pnlEmpContact.createParallelGroup(Alignment.LEADING)

									.addComponent(lblExtension)
									.addComponent(tfExtension, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)

						.addGroup(gl_pnlEmpContact.createParallelGroup(Alignment.LEADING)
							.addComponent(lblPhoto)
							.addComponent(tfPhoto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)

						)
			);
			pnlEmpContact.setLayout(gl_pnlEmpContact);

	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method initializing the design structure
	// of form left fields
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void initGroupLayoutleftPanel() {
		gl_pnlEmpDetails.setHorizontalGroup(
				gl_pnlEmpDetails.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlEmpDetails.createSequentialGroup()
						.addGap(10)
						.addGroup(gl_pnlEmpDetails.createParallelGroup(Alignment.LEADING)
							.addComponent(lblEmployeeID)
							.addComponent(lblBirthDate)
							.addComponent(lblTitle)
							.addComponent(lblLastName)
							.addComponent(lblFirstName)
							.addComponent(lblHireDate))
						.addGap(10)
						.addGroup(gl_pnlEmpDetails.createParallelGroup(Alignment.LEADING)
							.addComponent(dtcHireDate, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(dtcBirthDate, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(tfTitle, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(tfFirstName, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(tfLastName, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(tfEmployeeID, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
						.addContainerGap())
			);
			gl_pnlEmpDetails.setVerticalGroup(
				gl_pnlEmpDetails.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlEmpDetails.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pnlEmpDetails.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblEmployeeID)
							.addComponent(tfEmployeeID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlEmpDetails.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblLastName)
							.addComponent(tfLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlEmpDetails.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblFirstName)
							.addComponent(tfFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlEmpDetails.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblTitle)
							.addComponent(tfTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlEmpDetails.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBirthDate)
							.addComponent(dtcBirthDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlEmpDetails.createParallelGroup(Alignment.LEADING)
							.addComponent(lblHireDate)
							.addComponent(dtcHireDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(207))
			);

			pnlEmpDetails.setLayout(gl_pnlEmpDetails);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method contain all the code for creating events
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void createEvents() {
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFirstOnClick(e);
			}
		});
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLastOnClick(e);
			}
		});
		btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnSaveOnClick(e);
        	}
        });
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnPrevOnClick(evt);
		}
		});
		btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		btnAddOnClick(evt);
        	}
        });
		btnRemove.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnRemoveOnClick(e);
        	}
        });

		tfCountry.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				 btnNextOnClick(evt);
			}

		});
		tfSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent evt) {
        		tfSearchKeyReleased(evt);
        	}
        });
        tfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent evt) {
        		tfSearchFocusGained(evt);
        	}
        	@Override
        	public void focusLost(FocusEvent evt) {
        		tfSearchFocusLost(evt);
        	}
        });
        tfLastName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});

        tfAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});
        tfFirstName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});
        tfTitle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});
        tfHomePhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});
        tfExtension.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});
        tfPhoto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});
        tfCity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				updateChangedField (evt);
			}
		});
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// fetches empArray, and refreshes controls.
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void fetchAndRefresh() {
        empArray = EmployeeLogic.getInstance().getEmployees();
        currentEmployee = (!empArray.isEmpty()) ? 1 : null;
        inAddMode = (currentEmployee == null);
        refreshControls();
    }

    private void refreshControls() {
        refreshNavigation();
        refreshEmployeeFields();
        refreshDataButtons();
    }

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// updates the navigation controls.
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void refreshNavigation() {
        tfNavigation.setText((!inAddMode) ?
                "" + currentEmployee + " of " + empArray.size() :
                "" + (empArray.size() + 1) + " of " + (empArray.size() + 1));

        btnFirst.setEnabled(currentEmployee != null && currentEmployee > 1);
        btnPrev.setEnabled(currentEmployee != null && currentEmployee > 1);
        btnNext.setEnabled(currentEmployee != null && currentEmployee < empArray.size());
        btnLast.setEnabled(currentEmployee != null && currentEmployee < empArray.size());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// updates the empArray controls with a given employee's information.
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void refreshEmployeeFields() {
        Employee emp = (!inAddMode) ? empArray.get(currentEmployee - 1) : null;

        tfEmployeeID.setText((emp != null) ? ("" + emp.getEmployeeID()) : "(NEW)");
        tfLastName.setText((emp != null) ? emp.getLastName() : null);
        tfFirstName.setText((emp != null) ? emp.getFirstName() : null);
        tfTitle.setText((emp != null) ? emp.getTitle() : null);
        dtcBirthDate.setDate((emp != null) ? emp.getBirthDate() : null);
        dtcHireDate.setDate((emp != null) ? emp.getHireDate() : null);
        tfAddress.setText((emp != null) ? emp.getAddress() : null);
        tfCity.setText((emp != null) ? emp.getCity() : null);
        tfCountry.setText((emp != null) ? emp.getCountry() : null);
        tfHomePhone.setText((emp != null) ? emp.getHomePhone() : null);
        tfExtension.setText((emp != null) ? emp.getExtension() : null);
        tfPhoto.setText((emp != null) ? emp.getPhoto() : null);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//  Used after updating\adding employee - it retrieved updated data without loosing current employee location by its id
    //  fetches empArray, and tries to refresh controls to given employee.
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	   private void fetchAndRefresh(Long id) {
	       empArray = EmployeeLogic.getInstance().getEmployees();

	       if (!empArray.isEmpty()) {
	           if (id != null)
	               currentEmployee = (!inAddMode) ?
	                       empArray.indexOf(new Employee(id)) + 1 : empArray.size();  //

	           if (id == null || currentEmployee == 0) // indexOf could return -1.
	               currentEmployee = 1;
	       } else
	           currentEmployee = null;

	       inAddMode = (currentEmployee == null);
	       refreshControls();
	   }


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the various empArray manipulation buttons,
	// according to form state
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void refreshDataButtons() {
    	 btnSave.setEnabled(inAddMode);
         btnAdd.setEnabled(!inAddMode);
         btnRemove.setEnabled(!inAddMode);
         tfSearch.setEnabled(!inAddMode);



    }

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to present previous employee
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void btnPrevOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevOnClick
        inAddMode = false;
        currentEmployee--;
        refreshControls();
    }//GEN-LAST:event_btnPrevOnClick

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to present next employee
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void btnNextOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextOnClick
        currentEmployee++;
        refreshControls();
    }//GEN-LAST:event_btnNextOnClick

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to present last employee on list
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void btnLastOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastOnClick
        currentEmployee = empArray.size();
        refreshControls();
    }//GEN-LAST:event_btnLastOnClick

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to present first employee on list
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void btnFirstOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstOnClick
        inAddMode = false;
        currentEmployee = 1;
        refreshControls();
    }//GEN-LAST:event_btnFirstOnClick

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// On any change on employee fields updates the view to enable save
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void updateChangedField (java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfLastNameKeyTyped
        btnSave.setEnabled(true);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	// This method used to save changes on current employee Or adding new employee
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void btnSaveOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveOnClick
        System.out.print("Hello ");
    	if (inputValid()) {
            Long id = (!inAddMode) ?
                    Long.parseLong(tfEmployeeID.getText()) : -1;
            boolean saveLastStateTemp = inAddMode;
            boolean success = (!inAddMode) ?
                    EmployeeLogic.getInstance().editEmployee(id, tfLastName.getText(),
                            tfFirstName.getText(), tfTitle.getText(),
                            dtcBirthDate.getDate(), dtcHireDate.getDate(),
                            tfAddress.getText(), tfCity.getText(),
                            tfCountry.getText(), tfHomePhone.getText(),
                            tfExtension.getText(), tfPhoto.getText()) :
                    EmployeeLogic.getInstance().addEmployee(tfLastName.getText(),
                            tfFirstName.getText(), tfTitle.getText(),
                            dtcBirthDate.getDate(), dtcHireDate.getDate(),
                            tfAddress.getText(), tfCity.getText(),
                            tfCountry.getText(), tfHomePhone.getText(),
                            tfExtension.getText(), tfPhoto.getText());
            if (success) {
                fetchAndRefresh(id);
                if  (!saveLastStateTemp) {msgbox("Changes Saved Successfully");}
                else
               msgbox("Employee \""+tfLastName.getText()+" "+ tfFirstName.getText() +"\" has been added to list");

            } else
                JOptionPane.showMessageDialog(this, "Failure!"); // !!
        } else
            JOptionPane.showMessageDialog(this, "fname & lname are required!");

    }//GEN-LAST:event_btnSaveOnClick

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to empty form, enable save and navigation control to its last page
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void btnAddOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOnClick
        inAddMode = true;
        currentEmployee = empArray.size() + 1;
        refreshControls();
        tfLastName.requestFocus();
    }//GEN-LAST:event_btnAddOnClick

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Remove employee from DB
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void btnRemoveOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveOnClick
        String tempLastName = empArray.get(currentEmployee - 1).getLastName();
        String tempFirstName = empArray.get(currentEmployee - 1).getFirstName();
        System.out.println("here");
    	if (EmployeeLogic.getInstance()
                .removeEmployee(empArray.get(currentEmployee - 1).getEmployeeID())) {
            fetchAndRefresh((currentEmployee != 1) ?
                    empArray.get(currentEmployee - 2).getEmployeeID() : null);
            msgbox("Employee \""+tempLastName+" "+tempFirstName+"\" has been removed");
        } else
        	msgbox("Something went wrong!");
    }//GEN-LAST:event_btnRemoveOnClick

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	// empty search box hint text when text-box focused
 	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void tfSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSearchFocusLost
        if (tfSearch.getText().length() == 0)
            tfSearch.setText("Search");
    }//GEN-LAST:event_tfSearchFocusLost

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	// set search box hint text when text-box lost focused
 	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void tfSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSearchFocusGained
        if (tfSearch.getText().equals("Search"))
            tfSearch.setText(null);
    }//GEN-LAST:event_tfSearchFocusGained

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	// This method used to initiate search result and refresh view accordingly
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void tfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyReleased
            Integer foundIndex = null;
            String term = tfSearch.getText().toLowerCase();

            for (int i = 0; foundIndex == null && i < empArray.size(); i++) {
                Employee emp = empArray.get(i);

                if ( (emp.getEmployeeID() + "").contains(term) ||
                        emp.getLastName().toLowerCase().contains(term) ||
                        emp.getFirstName().toLowerCase().contains(term) ||
                        (emp.getTitle() != null &&
                            emp.getTitle().toLowerCase().contains(term)) ||
                        (emp.getBirthDate() != null &&
                            emp.getBirthDate().toString().contains(term)) ||
                        (emp.getHireDate() != null &&
                            emp.getHireDate().toString().contains(term)) ||
                        (emp.getAddress() != null &&
                            emp.getAddress().toLowerCase().contains(term)) ||
                        (emp.getCity() != null &&
                            emp.getCity().toLowerCase().contains(term)) ||
                        (emp.getCountry() != null &&
                            emp.getCountry().toLowerCase().contains(term)) ||
                        (emp.getHomePhone() != null &&
                            emp.getHomePhone().toLowerCase().contains(term)) ||
                        (emp.getExtension() != null &&
                            emp.getExtension().toLowerCase().contains(term)) ||
                        (emp.getPhoto() != null &&
                            emp.getPhoto().toLowerCase().contains(term)) )
                    foundIndex = i + 1;
            }

            if (foundIndex != null) {
                currentEmployee = foundIndex;
                refreshControls();
            }

    }//GEN-LAST:event_tfSearchKeyReleased

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	// This method used to generate alert box messages
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void msgbox(String s){
    	   JOptionPane.showMessageDialog(this, s);
    	}

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	// This method used to check if data is valid before saving it to DB
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean inputValid() {
        return (tfLastName.getText().length() > 0 &&
                tfFirstName.getText().length() > 0);
    }

    	private JPanel pnlEmpDetails = new JPanel();
	    private GroupLayout gl_pnlEmpDetails = new GroupLayout(pnlEmpDetails);
	    private JPanel pnlEmpContact = new JPanel();
	    private GroupLayout gl_pnlEmpContact = new GroupLayout(pnlEmpContact);
	    private JPanel pnlAddressDetails = new JPanel();
	    private GroupLayout gl_pnlAddressDetails = new GroupLayout(pnlAddressDetails);

		private JPanel contentPane;
		private JTextField tfEmployeeID;
		private JTextField tfLastName;
		private JTextField tfFirstName;
		private JLabel lblTitle;
		private JTextField tfTitle;


		private JTextField tfAddress = new JTextField();
		private JTextField tfCity;
		private JTextField tfCountry;
		private JTextField tfExtension;
		private JTextField tfPhoto;
		private JTextField tfHomePhone = new JTextField();
		private JTextField tfNavigation = new JTextField();

		private JLabel lblFirstName = new JLabel("First Name:");
		private JLabel lblLastName = new JLabel("Last Name:");
		private JLabel lblHireDate = new JLabel("Hire Date:");
		private JLabel lblEmployeeID = new JLabel("Employee ID:");
		private JLabel lblBirthDate = new JLabel("Birth Date:");
		private JLabel lblHomePhone = new JLabel("Home Phone:");
		private JLabel lblExtension = new JLabel("Extension:");
		private JLabel lblPhoto = new JLabel("Photo:");
		private JLabel lblCity = new JLabel("City:");
		private JLabel lblCountry = new JLabel("Country:");
		private JLabel lblAddress_1 = new JLabel("Address");
		private JLabel lblAddress = new JLabel("Address details:");

		private JDateChooser dtcBirthDate = new JDateChooser();
		private JDateChooser dtcHireDate = new JDateChooser();

		private JButton btnFirst = new JButton("|<");
		private JButton btnPrev = new JButton("<<");

		private JButton btnNext = new JButton(">>");
		private JButton btnLast = new JButton(">|");
		JButton btnNewButton = new JButton("New button");
		private final JButton btnSave = new JButton("Save");
		private final JButton btnAdd = new JButton("Add New");
		private final JButton btnRemove = new JButton("Delete");
		private JPanel pnlActionBtn;
		private JTextField tfSearch;
}
