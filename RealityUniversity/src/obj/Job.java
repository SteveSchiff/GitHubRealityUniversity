package obj;

/**
 * The Class Job.
 */
public class Job {

	/*********************************
	 * A very simple class that has 15
	 * attributes and one toString() method.
	 * The get and set methods are not counted
	 * as real 'methods'.
	 */	
	
	/*******************************
	 * Fields
	 ******************************/

	private int id;					// 01
	private String name;			// 02
	private String type;			// 03
	private String industry;		// 04
	private String category;		// 05
	private double annGrossSal;		// 06
	private double monGrossSal;		// 07
	private double marAnnualTax;	// 08
	private double marMonthlyTax;	// 09
	private double marAfterTax;		// 10
	private double sinAnnualTax;	// 11
	private double sinMonthlyTax;	// 12
	private double sinAfterTax;		// 13
	private int gpa;				// 14
	private double loan;			// 15
	
	// debug constant made for counting job
	// constructor calls
	//private static int count = 0;


	/**
	 * Constructor 1 of 2
	 */
	public Job() {
		// the next seven statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.print("==EMPTY JOB CONSTRUCTOR==  ");
	    System.out.println(methodName);
		// end of debugging statement set - 7 lines in all
	    
		this.id = 0;
		this.name = "";
		this.type = "";
		this.industry = "";
		this.category = "";
		this.type = "";
		this.annGrossSal = 0.0;
		this.monGrossSal = 0.0;
		this.marAnnualTax = 0.0;
		this.marMonthlyTax = 0.0;
		this.marAfterTax = 0.0;
		this.sinAnnualTax = 0.0;
		this.sinMonthlyTax = 0.0;
		this.sinAfterTax = 0.0;
		this.gpa = 0;
		this.loan = 0.0;
	}

	/**
	 * Constructor 2 of 2
	 */
	public Job(int id, String name, String type, String industry,
			String category, double annGrossSal, double monGrossSal,
			double marAnnualTax, double marMonthlyTax, double marAfterTax,
			double sinAnnualTax, double sinMonthlyTax, double sinAfterTax,
			int gpa, double loan) {	    
		super();	
		
		// the next six statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("");
	    System.out.println("JOB CONSTRUCTOR");
	    System.out.println(methodName);
		// end of debugging statement set - 6 lines in all
	    
		this.id = id;
		this.name = name;
		this.type = type;
		this.industry = industry;
		this.category = category;
		this.annGrossSal = annGrossSal;
		this.monGrossSal = monGrossSal;
		this.marAnnualTax = marAnnualTax;
		this.marMonthlyTax = marMonthlyTax;
		this.marAfterTax = marAfterTax;
		this.sinAnnualTax = sinAnnualTax;
		this.sinMonthlyTax = sinMonthlyTax;
		this.sinAfterTax = sinAfterTax;
		this.gpa = gpa;
		this.loan = loan;
	}
	
	/*********************************
	 * Getters/Setters
	 * 
	 * @param id
	 * @param name
	 * @param type
	 * @param industry
	 * @param category
	 * @param annGrossSal
	 * @param monGrossSal
	 * @param marAnnualTax
	 * @param marMonthlyTax
	 * @param marAfterTax
	 * @param sinAnnualTax
	 * @param sinMonthlyTax
	 * @param sinAfterTax
	 * @param gpa
	 * @param loan
	 ********************************/

	/**
	 * @return the id of the job
	 */

	public int getID() {
		return id;
	}

	/**
	 * Sets the id of the job.
	 * 
	 * @param id
	 *            : the new id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Gets the name of the job.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the job.
	 * 
	 * @param name
	 *            : the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type of the job
	 */

	public String getType() {
		return type;
	}

	/**
	 * Sets the type of the job.
	 * 
	 * @param type
	 *            : the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the industry of the job.
	 * 
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * Sets the industry of the job.
	 * 
	 * @param industry
	 *            : the new industry
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * Gets the category of the job.
	 * 
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category of the job.
	 * 
	 * @param category
	 *            : the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the gpa of the job
	 */

	public int getGPA() {
		return gpa;
	}

	/**
	 * Sets the gpa of the job.
	 * 
	 * @param gpa
	 *            : the new gpa
	 */
	public void setGPA(int gpa) {
		this.gpa = gpa;
	}

	/**
	 * Gets the annual gross salary of the job.
	 * 
	 * @return the annual gross salary
	 */
	public double getAnnGrossSal() {
		return annGrossSal;
	}

	/**
	 * Sets the annual gross salary of the job.
	 * 
	 * @param annGrossSal
	 *            : the new annual gross salary
	 */
	public void setAnnGrossSal(double annGrossSal) {
		this.annGrossSal = annGrossSal;
	}

	/**
	 * Gets the monthly gross salary of the job.
	 * 
	 * @return the monthly gross salary
	 */
	public double getMonGrossSal() {
		return monGrossSal;
	}

	/**
	 * Sets the monthly gross salary of the job.
	 * 
	 * @param monGrossSal
	 *            : the new monthly gross salary
	 */
	public void setMonGrossSal(double monGrossSal) {
		this.monGrossSal = monGrossSal;
	}

	/**
	 * Gets the annual married tax of the job.
	 * 
	 * @return the annual married tax
	 */
	public double getMarAnnualTax() {
		return marAnnualTax;
	}

	/**
	 * Sets the annual married tax of the job.
	 * 
	 * @param marAnnualTax
	 *            : the new monthly married tax
	 */
	public void setMarAnnualTax(double marAnnualTax) {
		this.marAnnualTax = marAnnualTax;
	}

	/**
	 * Gets the monthly married tax of the job.
	 * 
	 * @return the monthly married tax
	 */
	public double getMarMonthlyTax() {
		return marMonthlyTax;
	}

	/**
	 * Sets the monthly married tax of the job.
	 * 
	 * @param marMonthlyTax
	 *            : the new monthly married tax
	 */
	public void setMarMonthlyTax(double marMonthlyTax) {
		this.marMonthlyTax = marMonthlyTax;
	}

	/**
	 * Gets the tax after being married.
	 * 
	 * @return the tax after being married
	 */
	public double getMarAfterTax() {
		return marAfterTax;
	}

	/**
	 * Sets the tax after being married
	 * 
	 * @param marAfterTax
	 *            : the new tax after being married
	 */
	public void setMarAfterTax(double marAfterTax) {
		this.marAfterTax = marAfterTax;
	}

	/**
	 * Gets the single annual tax.
	 * 
	 * @return the annual single tax
	 */
	public double getSinAnnualTax() {
		return sinAnnualTax;
	}

	/**
	 * Sets the single annual tax.
	 * 
	 * @param sinAnnualTax
	 *            : the new annual single tax
	 */
	public void setSinAnnualTax(double sinAnnualTax) {
		this.sinAnnualTax = sinAnnualTax;
	}

	/**
	 * Gets the single monthly tax.
	 * 
	 * @return the single monthly tax
	 */
	public double getSinMonthlyTax() {
		return sinMonthlyTax;
	}

	/**
	 * Sets the single monthly tax.
	 * 
	 * @param sinMonthlyTax
	 *            : the new single monthly tax
	 */
	public void setSinMonthlyTax(double sinMonthlyTax) {
		this.sinMonthlyTax = sinMonthlyTax;
	}

	/**
	 * Gets the tax after being single.
	 * 
	 * @return the tax after being single
	 */
	public double getSinAfterTax() {
		return sinAfterTax;
	}

	/**
	 * Sets the tax after being single.
	 * 
	 * @param sinAfterTax
	 *            : the new single after tax
	 */
	public void setSinAfterTax(double sinAfterTax) {
		this.sinAfterTax = sinAfterTax;
	}
	/**
	 * Gets the job loan amount.
	 * 
	 * @return the loan amount
	 */
	public double getLoan() {
		return loan;
	}

	/**
	 * Sets the the job loan amount.
	 * 
	 * @param loan
	 *            : the new loan amount
	 */
	public void setLoan(double loan) {
		this.loan = loan;
	}

	@Override
	public String toString() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		return "Job [id=" + id + ", name=" + name + ", type=" + type + ", industry=" + industry
				+ ", category=" + category + ", annGrossSal=" + annGrossSal
				+ ", monGrossSal=" + monGrossSal + ", marAnnualTax="
				+ marAnnualTax + ", marMonthlyTax=" + marMonthlyTax
				+ ", marAfterTax=" + marAfterTax + ", sinAnnualTax="
				+ sinAnnualTax + ", sinMonthlyTax=" + sinMonthlyTax
				+ ", sinAfterTax=" + sinAfterTax + ", gpa=" + gpa + ", loan=" + loan + "]";
	}

}
