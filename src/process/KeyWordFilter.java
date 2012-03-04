package process;

public class KeyWordFilter implements CalendarFilter{
	String myCategory;
	String myKeyword;
	
	KeyWordFilter(String category, String keyword){
		myCategory = category;
		myKeyword = keyword;
	}

	@Override
	public boolean filter(Event e) {
		if(e.hasFeature(myCategory)){
			for(String s : e.getFeature(myCategory)){
				return s.contains(myKeyword);
			}
		}
		return false;
	}
	
	

}
