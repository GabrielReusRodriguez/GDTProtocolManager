package gdtManager.enumerations;

public enum ContentFields {
	
	//PROTOCOL LINES.
	GDT_SET_TYPE_ROOT_DATA_TRANSFER("8000"),
	GDT_FILE_SIZE("8100"),
	GDT_RECEIVER_ID("8315"),
	GDT_SENDER_ID("8316"),
	GDT_SET_TYPE_USED("9206"),
	GDT_VERSION_NUMBER_GDT("9218"),

	//PATIENT LINES
	PATIENT_NUMBER("3000"),
	PATIENT_NAME("3101"),
	PATIENT_FIRST_NAME("3102"),
	PATIENT_DATE_OF_BIRTH("3103"),
	PATIENT_TITLE("3104"),
	PATIENT_INSURANCE_NUMBER("3105"),
	PATIENT_RESIDENCE("3106"),
	PATIENT_STREET("3107"),
	PATIENT_INSURANCE_STATUS("3108"),
	PATIENT_SEX("3110"),
	PATIENT_HEIGHT("3622"),
	PATIENT_WEIGHT("3623"),
	PATIENT_FIRST_LANGUAGE("3628"),
	
	//Data
	CURRENT_DIAGNOSIS("6205"),
	RESULTS("6220"),
	THIRD_PARTY_RESULTS("6221"),
	COMMENTS("6227")
	
	
	;
	
	
	private String contentField;
	
	ContentFields(String content){
		this.contentField = content;
	}
	
	public String contentField() {
		return contentField;
	}
	
	public int getLenght() {
		int length = 0;
		length = this.contentField.length();
		return length;
	}
	
}
