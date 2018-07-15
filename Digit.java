/**
* Created on 20 April, 2018
* @author: Emma Jing
*/

public class Digit {

	private Digit parent = null;
	private int self;
	private int position = 0;
	private int hValue = 0;
	private int path = 0;
	private int order = 0;

	public Digit(Digit parent, int self, int position, int hValue, int order){
		this.parent = parent;
		this.self = self;
		this.position = position;
		this.hValue= hValue;
		this.order = order;
	}
	public Digit(Digit parent, int self, int position, int hValue, int path, int order){
		this.parent = parent;
		this.self = self;
		this.position = position;
		this.hValue= hValue;
		this.path = path;
		this.order = order;
	}
	public Digit(Digit parent, int self, int position){
		this.parent = parent;
		this.self = self;
		this.position = position;
	}

	public int getHValue() {
		return hValue;
	}
	public void setHValue(int hValue) {
		this.hValue = hValue;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getSelf() {
		return self;
	}
	public void setSelf(int self) {
		this.self = self;
	}
	public Digit getParent() {
		return parent;
	}
	public void setParent(Digit parent) {
		this.parent = parent;
	}
	public int getPath() {
		return path;
	}
	public void setPath(int path) {
		this.path = path;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
