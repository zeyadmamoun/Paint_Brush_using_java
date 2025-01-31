import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.lang.Math;

class Rectangle extends GeoShape{
	
	Rectangle(Point s,Point y,Color c,Boolean i){
		super(s,y);
		setColor(c);
		setIsSolid(i);
	}
	
	void draw(Graphics g){
		g.setColor(getColor());
		Point start = getStartPoint();
		Point end = getEndPoint();
		if(isSolid)
			g.fillRect(start.x,start.y,Math.abs(start.x - end.x),Math.abs(start.y - end.y));
		else
			g.drawRect(start.x,start.y,Math.abs(start.x - end.x),Math.abs(start.y - end.y));
	}
	
}