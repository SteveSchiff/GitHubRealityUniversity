package gui.dialogs;

import gui.GuiInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import obj.Job;
import ctrl.Controller;

public class ManageJobs extends JDialog implements GuiInterface {

	/** An instance of ManageJobs. */
	private static ManageJobs manageJobsInstance = null;
	private List<Job> listOfJobs = Controller.getControllerInstance()
			.getJobsList();

	// private List<Job> jobRows = new ArrayList<>();
	private JTable jobsTable = new JTable() { // This is a declared object.
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return false;
		}; // end isCellEditable method
	}; // end tblJobs declaration

	private DefaultTableModel mdlJobs;

	private List<String> columns = new ArrayList<>();

	protected final JButton btnApply = new JButton("Apply");

	public ManageJobs() {

		setIconImage(SM_SETTINGS);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(SETTINGS_WIDTH, SETTINGS_HEIGHT));
		setTitle("Manage Jobs");
		setLocationRelativeTo(Controller.getControllerInstance().getFrame());
		setSize(new Dimension(SETTINGS_WIDTH, SETTINGS_HEIGHT));

		getContentPane().add(drawHeader(), BorderLayout.NORTH);
		getContentPane().add(drawContent(), BorderLayout.CENTER);
		getContentPane().add(drawFooter(), BorderLayout.SOUTH);

		pack();
		setVisible(true);

	} // end constructor

	/**
	 * Gets the single instance of ManageJobs.
	 * 
	 * @return single instance of ManageJobs
	 */
	public static ManageJobs getManageJobsInstance() {

		// If we have an instance, simply return it.
		// Otherwise, create a new one.
		if (manageJobsInstance != null) {

			return manageJobsInstance;
		} else {
			manageJobsInstance = new ManageJobs();

			return manageJobsInstance;
		}
	} // end getManageJobsInstance() method

	public JPanel drawHeader() {

		JPanel pnlHeader = new JPanel();
		// TODO: Put filters/search options here!

		return pnlHeader;
	} // -- end drawHeader() method

	public JScrollPane drawContent() {

		JScrollPane pnlContent = new JScrollPane();

		JTable tblJobs = buildTable();
		tblJobs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		pnlContent.setViewportView(tblJobs);

		return pnlContent;
	} // -- end drawContent() method

	public JTable buildTable() {

		// Build columns
		columns.add("ID");
		columns.add("Name");
		columns.add("Type");
		columns.add("Industry");
		columns.add("Category");
		columns.add("Salary");
		columns.add("GPA");

		// Build data
		final List<Object> jobRowData = new ArrayList<>();

		for (int i = 0; i < listOfJobs.size(); i++) {
			Object[] jobData = new Object[columns.size()];

			jobData[0] = listOfJobs.get(i).getID();
			jobData[1] = listOfJobs.get(i).getName();
			jobData[2] = listOfJobs.get(i).getType();
			jobData[3] = listOfJobs.get(i).getIndustry();
			jobData[4] = listOfJobs.get(i).getCategory();
			jobData[5] = FMT_CURRENCY
					.format(listOfJobs.get(i).getAnnGrossSal());
			jobData[6] = ARR_GPA[listOfJobs.get(i).getGPA()];

			jobRowData.add(jobData);
		}

		mdlJobs = new DefaultTableModel(jobRowData.toArray(new Object[][] {}),
				columns.toArray());
		jobsTable.setModel(mdlJobs);

		final TableRowSorter<TableModel> sorter = new TableRowSorter<>(
				jobsTable.getModel());

		// Make ID's sort correctly
		class IntComparator implements Comparator<Object> {
			public int compare(Object o1, Object o2) {

				Integer int1 = (Integer) o1;
				Integer int2 = (Integer) o2;

				return int1.compareTo(int2);
			}

			public boolean equals(Object o2) {

				return this.equals(o2);
			}
		} // end inner class

		sorter.setComparator(0, new IntComparator());

		jobsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jobsTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		jobsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jobsTable.setFillsViewportHeight(true);
		jobsTable.setRowSorter(sorter);

		// Delete key removes row
		jobsTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				int c = e.getKeyCode();
				if (c == KeyEvent.VK_DELETE) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					String columnName = target.getColumnModel().getColumn(0)
							.getHeaderValue().toString();

					// Get the job from the selected row and remove it
					Controller.getControllerInstance()
							.removeJobFromListOfNewJobs(
									Controller.getControllerInstance().getJob(
											columnName,
											String.valueOf(getCellData(target,
													row, 0))));
					btnApply.setEnabled(true);
					mdlJobs.removeRow(row);
					mdlJobs.fireTableDataChanged();
				}

			} // -- end keyPressed() method
		});

		// Double-click to edit
		jobsTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					String columnName = target.getColumnModel().getColumn(0)
							.getHeaderValue().toString();

					// Get the job from the selected row and open it for editing
					Controller.getControllerInstance().openEditJob(
							Controller.getControllerInstance()
									.getJob(columnName,
											String.valueOf(getCellData(target,
													row, 0))));

					setVisible(false);
				}

			} // -- end mouseClicked() method
		});

		return jobsTable;
	} // -- end buildTable() method

	public JPanel drawFooter() {

		JPanel pnlFooter = new JPanel();
		GridBagLayout gbl_pnlFooter = new GridBagLayout();
		gbl_pnlFooter.columnWidths = new int[] { 0, 452, 0 };
		gbl_pnlFooter.rowHeights = new int[] { 15, 0, 0 };
		gbl_pnlFooter.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlFooter.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pnlFooter.setLayout(gbl_pnlFooter);

		JLabel lblInfo = new JLabel("  Double-click to edit.");
		lblInfo.setFont(BORDER_FONT);

		GridBagConstraints gbc_lblInfo = new GridBagConstraints();
		gbc_lblInfo.anchor = GridBagConstraints.NORTH;
		gbc_lblInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInfo.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfo.gridx = 0;
		gbc_lblInfo.gridy = 0;
		pnlFooter.add(lblInfo, gbc_lblInfo);

		JPanel pnlAddJob = new JPanel();
		GridBagConstraints gbc_pnlAddJob = new GridBagConstraints();
		gbc_pnlAddJob.insets = new Insets(0, 0, 0, 5);
		gbc_pnlAddJob.gridx = 0;
		gbc_pnlAddJob.gridy = 1;
		pnlFooter.add(pnlAddJob, gbc_pnlAddJob);
		JButton btnAddJob = new JButton("New Job");
		pnlAddJob.add(btnAddJob);
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnOk = new JButton("OK");
		JButton btnCancel = new JButton("Cancel");

		pnlButtons.add(btnOk);
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnApply);
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.anchor = GridBagConstraints.EAST;
		gbc_pnlButtons.gridx = 1;
		gbc_pnlButtons.gridy = 1;
		pnlFooter.add(pnlButtons, gbc_pnlButtons);
		btnApply.setEnabled(false);

		// Apply Changes
		btnApply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Controller.getControllerInstance().saveJobs(true);
				btnApply.setEnabled(false);
				mdlJobs.fireTableDataChanged();

			} // -- end actionPerformed() method
		});

		// Apply Changes and Close Window
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Controller.getControllerInstance().saveJobs(true);
				mdlJobs.fireTableDataChanged();
				dispose();
			} // -- end actionPerformed() method
		});

		// Cancel Changes and Close Window
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Controller.getControllerInstance().saveJobs(false);
				mdlJobs.fireTableDataChanged();
				dispose();

			} // -- end actionPerformed() method
		});

		// Add new Job
		btnAddJob.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new EditJob(null);
				setVisible(false);

			} // -- end actionPerformed() method

		});

		return pnlFooter;
	} // end drawFooter() method

	public Object getCellData(JTable table, int row_index, int col_index) {

		return table.getModel().getValueAt(row_index, col_index);
	} // -- end getCellData() method

	public void addJobToTable(Job job) {

		DefaultTableModel model = (DefaultTableModel) jobsTable.getModel();

		model.addRow(new Object[] { "*", job.getName(), job.getType(),
				job.getIndustry(), job.getCategory(),
				FMT_CURRENCY.format(job.getAnnGrossSal()),
				ARR_GPA[job.getGPA()] });
		btnApply.setEnabled(true);
		mdlJobs.fireTableDataChanged();

	} // -- end addJobToTable() method
} // end ManageJobs class
