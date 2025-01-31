import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;

class Line extends GeoShape{
	
	Line(Point s,Point y,Color c){
		super(s,y);
		setColor(c);
	}
	
	void draw(Graphics g){
		g.setColor(getColor());
		Point start = getStartPoint();
		Point end = getEndPoint();
		g.drawLine(start.x,start.y,end.x,end.y);
	}
	
}