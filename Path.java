import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.lang.Math;
import java.util.ArrayList;

class Path extends GeoShape{
	public ArrayList<Point> drawPath = new ArrayList<Point>();
	
	Path(Point s,Point y,Color c){
		super(s,y);
		setColor(c);
	}
	
	void draw(Graphics g){
		g.setColor(getColor());
		
		if(drawPath.size() > 0){
			for(Point p: drawPath){
				g.fillOval(p.x,p.y,10,10);	
			}
		}
	}
	
}