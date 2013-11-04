package obj;

/**
 * The Class Survey.
 */
public class Survey {

	/*********************************
	 * A very simple class that has 22
	 * attributes and one toString() method.
	 * The get and set methods are not counted
	 * as real 'methods'.
	 */
	
	/*********************************
	 * Fields
	 ********************************/
	private int id;					// 01
	private String firstName;		// 02
	private String lastName;		// 03
	private int gpa;				// 04
	private int gender;				// 05
	private int classPeriod;		// 06
	private String teacher;			// 07
	private int groupID;			// 08
	private int education;			// 09
	private int preferredJob;		// 10
	private int assignedJob;		// 11
	private int married;			// 12
	private int spouse;				// 13
	private int children;			// 14
	private int creditCards;		// 15
	private int creditCardUses;		// 16
	private String groceries;		// 17
	private String clothing;		// 18
	private String home;			// 19
	private String vehicle;			// 20
	private double childSupport;	// 22
	private int creditScore;		// 23

	/**
	 * Constructor
	 */
	public Survey() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("EMPTY SURVEY CONSTRUCTOR");
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		this.id = 0;				// 01
		this.firstName = "";		// 02
		this.lastName = "";			// 03
		this.gpa = 0;				// 04
		this.gender = 0;			// 05
		this.classPeriod = 0;		// 06
		this.teacher = "";			// 07
		this.groupID = 0;			// 08
		this.education = 0;			// 09
		this.preferredJob = 0;		// 10
		this.assignedJob = 0;		// 11
		this.married = 0;			// 12
		this.spouse = 0;			// 13
		this.children = 0;			// 14
		this.creditCards = 0;		// 15
		this.creditCardUses = 0;	// 16
		this.groceries = "";		// 17
		this.clothing = "";			// 18
		this.home = "";				// 19
		this.vehicle = "";			// 20
		this.childSupport = 0;		// 21
		this.creditScore = 0;		// 22
		this.assignedJob = 1;		// 23
	}

	/*********************************
	 * Getters/Setters
	 ********************************/

	/**
	 * @return id of the survey
	 */
	public int getID() {
		return id;
	}

	/**
	 * Sets the id of the survey.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Gets the first name of the survey.
	 * 
	 * @return the first name of the survey
	 */
	public String getFName() {
		return firstName;
	}

	/**
	 * Sets the first name of the survey.
	 * 
	 * @param fName
	 *            the new first name of the survey
	 */
	public void setFName(String fName) {
		this.firstName = fName;
	}

	/**
	 * Gets the l name of the survey.
	 * 
	 * @return the l name of the survey
	 */
	public String getLName() {
		return lastName;
	}

	/**
	 * Sets the l name of the survey.
	 * 
	 * @param lName
	 *            the new l name of the survey
	 */
	public void setLName(String lName) {
		this.lastName = lName;
	}

	/**
	 * Gets the gpa of the survey.
	 * 
	 * @return the gpa of the survey
	 */
	public int getGPA() {
		return gpa;
	}

	/**
	 * Sets the gpa of the survey.
	 * 
	 * @param gpa
	 *            the new gpa of the survey
	 */
	public void setGPA(int gpa) {
		this.gpa = gpa;
	}

	/**
	 * Gets the gender of the survey.
	 * 
	 * @return the gender of the survey
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the survey.
	 * 
	 * @param gender
	 *            the new gender of the survey
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}

	/**
	 * Gets the c period of the survey.
	 * 
	 * @return the c period of the survey
	 */
	public int getCPeriod() {
		return classPeriod;
	}

	/**
	 * Sets the c period of the survey.
	 * 
	 * @param cPeriod
	 *            the new c period of the survey
	 */
	public void setCPeriod(int cPeriod) {
		this.classPeriod = cPeriod;
	}

	/**
	 * Gets the teacher of the survey.
	 * 
	 * @return the teacher of the survey
	 */
	public String getTeacher() {
		return teacher;
	}

	/**
	 * Sets the teacher of the survey.
	 * 
	 * @param teacher
	 *            the new teacher of the survey
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * Gets the group id of the survey.
	 * 
	 * @return the group id of the survey
	 */
	public int getGroupID() {
		return groupID;
	}

	/**
	 * Sets the group id of the survey.
	 * 
	 * @param groupID
	 *            the new group id of the survey
	 */
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	/**
	 * Gets the education of the survey.
	 * 
	 * @return the education of the survey
	 */
	public int getEducation() {
		return education;
	}

	/**
	 * Sets the education of the survey.
	 * 
	 * @param education
	 *            the new education of the survey
	 */
	public void setEducation(int education) {
		this.education = education;
	}

	/**
	 * Gets the pref job of the survey.
	 * 
	 * @return the pref job of the survey
	 */
	public int getPreferredJob() {
		return preferredJob;
	}

	/**
	 * Sets the pref job of the survey.
	 * 
	 * @param prefJob
	 *            the new pref job of the survey
	 */
	public void setPreferredJob(int prefJob) {
		this.preferredJob = prefJob;
	}

	/**
	 * Gets the job of the survey.
	 * 
	 * @return the job of the survey
	 */
	public int getAssignedJob() {
		return assignedJob;
	}

	/**
	 * Sets the job of the survey.
	 * 
	 * @param jobID
	 *            the new job of the survey
	 */
	public void setAssignedJob(int jobID) {
		this.assignedJob = jobID;
	}

	/**
	 * Gets the married status of the survey.
	 * 
	 * @return 0: no<br>
	 *         1: yes
	 */
	public int getMarried() {
		return married;
	}

	/**
	 * Sets the married status of the survey.
	 * 
	 * @param married
	 *            the new married status of the survey
	 */
	public void setMarried(int married) {
		this.married = married;
	}

	/**
	 * Gets the children status of the survey.
	 * 
	 * @return 0: None<br>
	 *         1: Yes
	 */
	public int getChildren() {
		return children;
	}
	
	/**
	 * Gets the spouse of the survey.
	 * 
	 * @return the spouse of the survey
	 */
	public int getSpouse() {
		return spouse;
	}

	/**
	 * Sets the spouse of the survey.
	 * 
	 * @param spouse
	 *            the new spouse of the survey
	 */
	public void setSpouse(int spouse) {
		this.spouse = spouse;
	}

	/**
	 * Sets the children status of the survey.
	 * 
	 * @param children
	 *            the new children status of the survey
	 */
	public void setChildren(int children) {
		this.children = children;
	}

	/**
	 * Gets the credit cards status of the survey.
	 * 
	 * @return 0: None<br>
	 *         1: Yes
	 */
	public int getCreditCards() {
		return creditCards;
	}

	/**
	 * Sets the credit cards status of the survey.
	 * 
	 * @param cCards
	 *            the new credit cards status of the survey
	 */
	public void setCreditCards(int cCards) {
		this.creditCards = cCards;
	}

	/**
	 * Gets the credit card use.
	 * 
	 * @return 0: Emergency<br>
	 *         1: Non-Emergency<br>
	 *         2: Not Applicable
	 */
	public int getCreditCardUses() {
		return creditCardUses;
	}

	/**
	 * Sets the credit card use.
	 * 
	 * @param cCardUses
	 *            the new credit card use
	 */
	public void setCreditCardUses(int cCardUses) {
		this.creditCardUses = cCardUses;
	}

	/**
	 * Gets the groceries.
	 * 
	 * @return the groceries
	 */
	public String getGroceries() {
		return groceries;
	}

	/**
	 * Sets the groceries of the survey.
	 * 
	 * @param groceries
	 *            the new groceries of the survey
	 */
	public void setGroceries(String groceries) {
		this.groceries = groceries;
	}

	/**
	 * Gets the clothing of the survey.
	 * 
	 * @return the clothing of the survey
	 */
	public String getClothing() {
		return clothing;
	}

	/**
	 * Sets the clothing of the survey.
	 * 
	 * @param clothing
	 *            the new clothing of the survey
	 */
	public void setClothing(String clothing) {
		this.clothing = clothing;
	}

	/**
	 * Gets the home of the survey.
	 * 
	 * @return the home of the survey
	 */
	public String getHome() {
		return home;
	}

	/**
	 * Sets the home of the survey.
	 * 
	 * @param home
	 *            the new home of the survey
	 */
	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * Gets the vehicle of the survey.
	 * 
	 * @return the vehicle of the survey
	 */
	public String getVehicle() {
		return vehicle;
	}

	/**
	 * Sets the vehicle of the survey.
	 * 
	 * @param vehicle
	 *            the new vehicle of the survey
	 */
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * Gets the entertainment of the survey.
	 * 
	 * @return the entertainment of the survey
	 */
	public double getChildSupport() {
		return childSupport;
	}

	/**
	 * Sets the entertainment of the survey.
	 * 
	 * @param entertainment
	 *            the new entertainment of the survey
	 */
	public void setChildSupport(double childSupport) {
		this.childSupport = childSupport;
	}

	/**
	 * Gets the savings of the survey.
	 * 
	 * @return the savings of the survey
	 */
	public int getCreditScore() {
		return creditScore;
	}

	/**
	 * Sets the savings of the survey.
	 * 
	 * @param savings
	 *            the new savings of the survey
	 */
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	@Override
	public String toString() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		return "Survey [id=" + id + ", fName=" + firstName + ", lName=" + lastName
				+ ", gpa=" + gpa + ", gender=" + gender + ", cPeriod="
				+ classPeriod + ", teacher=" + teacher + ", groupID=" + groupID
				+ ", education=" + education + ", prefJob=" + preferredJob
				+ ", assignedJob=" + assignedJob + ", married=" + married
				+ ", spouse=" + spouse + ", children=" + children + ", cCards="
				+ creditCards + ", cCardUses=" + creditCardUses + ", groceries="
				+ groceries + ", clothing=" + clothing + ", home=" + home
				+ ", vehicle=" + vehicle + ", entertainment=" + childSupport
				+ ", savings=" + creditScore + "]";
	}	

	
}
