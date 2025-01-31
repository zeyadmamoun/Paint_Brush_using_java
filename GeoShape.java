import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;

abstract public class GeoShape {
	
	private Point startPoint;
	private Point endPoint;
	private Color color;
	boolean isSolid;
	
	//constructor.
	GeoShape(Point start,Point end){
		startPoint = new Point(start.x,start.y);
		endPoint = new Point(end.x,end.y);
	}
	
	//getters and setters.
	void setStartPoint(int x,int y){
		startPoint.x = x;
		startPoint.y = y;
	}
	
	Point getStartPoint(){
		return startPoint;
	}
	
	void setEndPoint(int x,int y){
		endPoint.x = x;
		endPoint.y = y;
	}
	
	Point getEndPoint(){
		return endPoint;
	}
	
	void setColor(Color c){
		color = c;
	}
	
	Color getColor(){
		return color;
	}
	
	void setIsSolid(Boolean i){
		isSolid = i;
	}
	
	Boolean getIsSolid(){
		return isSolid;
	}
	
	//methods
	abstract void draw(Graphics g);

}