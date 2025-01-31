import java.applet.Applet;
import java.awt.Graphics;
import java.lang.Math;
import java.awt.Point;
import java.awt.Color;
import java.awt.Label;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Canvas extends Applet{
	// constants represents the states.
	// Declare constants for the shapes.
	public static final int NO_SHAPE = 0;
    public static final int LINE_SHAPE = 1;
    public static final int RECT_SHAPE = 2;
	public static final int OVAL_SHAPE = 3;
	public static final int BRUSH = 4;
	public static final int ERASER = 5;
	
	//function buttons.
	Button clearBtn = new Button("Clear");
	Button undoBtn = new Button("Undo");
	//shapes buttons.
	Button lineBtn = new Button("Line");
	Button rectBtn = new Button("Rectangle");
	Button ovalBtn = new Button("Oval");
	Button brushBtn = new Button("Brush");
	Button eraserBtn = new Button("Eraser");
	Checkbox solidBox = new Checkbox("Solid",false);
	// color buttons.
	Button blackBtn = new Button("Black");
	Button redBtn = new Button("Red");
	Button greenBtn = new Button("Green");
	Button blueBtn = new Button("Blue");
	
	//canvas attributes.
	int shape_state = LINE_SHAPE;
	boolean isSolid_state = false;
	Point appletStartPoint = new Point();
	Point appletEndPoint = new Point();
	int x = 0;
	int y = 0;
	int tempWidth = 0;
	int tempHeight = 0;
	ArrayList<GeoShape> shapes = new ArrayList<GeoShape>();
	ArrayList<Point> tempPath = new ArrayList<Point>();
	Color tempColor = Color.BLACK;
	Color eraserColor = Color.WHITE;
	
	public void init(){
		// building tools panal.
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(new Label("Functions:"));
		add(clearBtn);
		add(undoBtn);
		add(new Label("Paint Mode:"));
		add(lineBtn);
		add(rectBtn);
		add(ovalBtn);
		add(brushBtn);
		add(eraserBtn);
		add(solidBox);
		add(new Label("Color:"));
		add(blackBtn);
		add(redBtn);
		add(greenBtn);
		add(blueBtn);
		
		//coloring the color buttons.
		blackBtn.setBackground(Color.BLACK);
		redBtn.setBackground(Color.RED);
		greenBtn.setBackground(Color.GREEN);
		blueBtn.setBackground(Color.BLUE);
		
		//set Action Commands.
		clearBtn.setActionCommand("clr");
		undoBtn.setActionCommand("undo");
		lineBtn.setActionCommand("line");
		rectBtn.setActionCommand("rectangle");
		ovalBtn.setActionCommand("oval");
		brushBtn.setActionCommand("brush");
		eraserBtn.setActionCommand("ers");
		blackBtn.setActionCommand("black");
		redBtn.setActionCommand("red");
		greenBtn.setActionCommand("green");
		blueBtn.setActionCommand("blue");
		
		// binding the buttons with our action listener.
		ButtonsListener listener = new ButtonsListener();
		clearBtn.addActionListener(listener);
		undoBtn.addActionListener(listener);
		lineBtn.addActionListener(listener);
		rectBtn.addActionListener(listener);
		ovalBtn.addActionListener(listener);
		blackBtn.addActionListener(listener);
		redBtn.addActionListener(listener);
		greenBtn.addActionListener(listener);
		blueBtn.addActionListener(listener);
		brushBtn.addActionListener(listener);
		eraserBtn.addActionListener(listener);
		solidBox.addItemListener(listener);
		
		MouseMovementHandler handler = new MouseMovementHandler();
		this.addMouseMotionListener(handler);
		this.addMouseListener(handler);
	}
	
	public void paint(Graphics g) {
		if(shapes.size() > 0){
			for(GeoShape sh: shapes){
				sh.draw(g);
			}
		}
		
		g.setColor(tempColor);
		switch(shape_state){
			case NO_SHAPE:
				break;
			case LINE_SHAPE:
				g.drawLine(appletStartPoint.x,appletStartPoint.y,appletEndPoint.x,appletEndPoint.y);
				break;
			case OVAL_SHAPE:
				if(isSolid_state)
					g.fillOval(x,y,tempWidth,tempHeight);
				else
					g.drawOval(x,y,tempWidth,tempHeight);
				break;
			case RECT_SHAPE:
				if(isSolid_state)
					g.fillRect(x,y,tempWidth,tempHeight);
				else
					g.drawRect(x,y,tempWidth,tempHeight);
				break;
			case BRUSH:
					if(tempPath.size() > 0){
						for(Point p: tempPath){
							g.fillOval(p.x,p.y,10,10);
						}
					}
					g.drawOval(appletEndPoint.x,appletEndPoint.y,10,10);
				break;
			case ERASER:
					g.setColor(eraserColor);
					if(tempPath.size() > 0){
						for(Point p: tempPath){
							g.fillOval(p.x,p.y,10,10);
						}
					}
					g.drawOval(appletEndPoint.x,appletEndPoint.y,10,10);
				break;
		}
			
	}
	
	
	// here we change the canvas states due to user clicks.
	class ButtonsListener implements ActionListener, ItemListener {
		
		// listener to the checkbox.
		public void itemStateChanged(ItemEvent e){
			if(e.getStateChange() == e.SELECTED){
				isSolid_state = true;
			} else {
				isSolid_state = false;
			}
		}
		//listenr to all the buttons.
		public void actionPerformed(ActionEvent e){
			
			String command = e.getActionCommand();
			switch(command){
				case "line":
					shape_state = LINE_SHAPE;
					break;
				case "oval":
					shape_state = OVAL_SHAPE;
					break;
				case "rectangle":
					shape_state = RECT_SHAPE;
					break;
				case "black":
					tempColor = Color.BLACK;
					break;
				case "green":
					tempColor = Color.GREEN;
					break;
				case "red":
					tempColor = Color.RED;
					break;
				case "blue":
					tempColor = Color.BLUE;
					break;
				case "clr":
					shape_state = NO_SHAPE;
					shapes.clear();
					repaint();
					break;
				case "undo":
					if(shapes.size() > 0){
						shape_state = NO_SHAPE;
						shapes.remove(shapes.size() - 1);
						repaint();	
					}
					break;
				case "brush":
					shape_state = BRUSH;
					break;
				case "ers":
					shape_state = ERASER;
					break;
			}
		}
	}
	
	// this listener change the appletStartPoint and appletEndPoint and save the new shape when released.
	class MouseMovementHandler extends MouseAdapter {
		
		public void mousePressed(MouseEvent e){
			appletStartPoint.x = e.getX();
			appletStartPoint.y = e.getY();
		}
		
		public void mouseDragged(MouseEvent e){
			appletEndPoint.x = e.getX();
			appletEndPoint.y = e.getY();
			
			//here we check for the minimum point because end point might be smaller than start point
			//when we draw to the left.
			if(shape_state == OVAL_SHAPE || shape_state == RECT_SHAPE){
				x = Math.min(appletStartPoint.x, appletEndPoint.x);
				y = Math.min(appletStartPoint.y, appletEndPoint.y);
				
				tempWidth = Math.abs(appletStartPoint.x - appletEndPoint.x);
				tempHeight = Math.abs(appletStartPoint.y - appletEndPoint.y);
				
			}else if(shape_state == BRUSH || shape_state == ERASER){
				tempPath.add(new Point(appletEndPoint));
			}
			repaint();
		}
		
		public void mouseReleased(MouseEvent e) {
			//references to the shape and start / end points.
			GeoShape newShape;
			Point startPoint;
			Point endPoint;
			Path newPath;
			
			if(appletStartPoint.x == appletEndPoint.x && appletStartPoint.y == appletEndPoint.y )
				return;
			switch(shape_state){
				case LINE_SHAPE:
					newShape = new Line(appletStartPoint,appletEndPoint,tempColor);
					shapes.add(newShape);
					break;
				case OVAL_SHAPE:
					startPoint = new Point(x,y);
					endPoint = new Point(x + tempWidth,y + tempHeight);
					newShape = new Oval(startPoint,endPoint,tempColor,isSolid_state);
					shapes.add(newShape);
					break;
				case RECT_SHAPE:
					startPoint = new Point(x,y);
					endPoint = new Point(x + tempWidth,y + tempHeight);
					newShape = new Rectangle(startPoint,endPoint,tempColor,isSolid_state);
					shapes.add(newShape);
					break;
				case BRUSH:
					newPath = new Path(appletStartPoint,appletEndPoint,tempColor);
					newPath.drawPath.addAll(tempPath);
					shapes.add(newPath);
					tempPath.clear();
					break;
				case ERASER:
					newPath = new Path(appletStartPoint,appletEndPoint,eraserColor);
					newPath.drawPath.addAll(tempPath);
					shapes.add(newPath);
					tempPath.clear();
					break;
			}
		}
	}
}