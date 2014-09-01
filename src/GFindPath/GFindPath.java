package GFindPath;

import java.util.Vector;

class Stack {
	public int[] st;
	public int top;
	public int count;
	
	public Stack(int size) {
		st = new int[size];
		for(int i=0; i<size; i++)
			st[i] = -1;
		top = -1;
		count = 0;
	}

	public void push(int j) {
		count++;
		st[++top] = j;
	}

	public int pop() {
		count--;
		return st[top--];
		
	}

	public int peek() {
		return st[top];
	}

	public boolean isEmpty() {
		count--;
		return (top == -1);
	}

	//打印栈里的所有元素
	public void list() {
		for (int i = 0; i<count; i++) {
			System.out.print(st[i] + "   ");
		}
		System.out.println();
	}
	
	public int getCount() {
		return count;
	}

	public boolean isContains(int i) {		
		for (int k = 0; k < count; k++) {
			if (st[k] == i) {
				return true;
			}
		}
		return false;
	}
	
	public void clean() {
		for(int i=0; i<st.length; i++)
			st[i] = -1;
		top = -1;
		count = 0;
	}
}

class Vertex {
	public int value;
	public int area;
	public boolean isVisited;
	
	Vertex(int value, int area) {
		this.value = value;
		this.area = area;
	}
}

class Graph {
	public Vector<Vertex> vertexs;//存放图的顶点
	public Vector[] adjVertex;//存放每个顶点的相邻顶点
	public Stack stack;
	public int[][] adjMat;//图相邻矩阵
	public int vertexnum;//顶点数

	Graph() {}
	
	Graph(int size) {
		vertexnum = size;
		vertexs = new Vector<Vertex>(size);
		adjVertex = new Vector[size];
		stack = new Stack(size);
	}

	void add(Vertex vertex) {
		vertexs.add(vertex);
	}

	
//	初始化相邻矩阵
	void initAdjMat(int [][] adjMat) {
		this.adjMat = adjMat;
	}

}

class Area extends Graph {
	public int areaNum;
	
	Area() {}
	
	Area(int size, int areaNum) {
		super(size);
		this.areaNum = areaNum;	
	}
}
/*class MainArea extends Area {
	
	MainArea(int size) {
		super(size);
		
	}
}

class AdjArea extends Area {
	
	AdjArea(int size) {
		super(size);
		
	}
}*/

class AreaGraph extends Graph{
	public Area mainArea;//主区域
	public Vector<Area> areaVector;
	public double tempPro;//临时存放顶点到顶点的概率
	public double[][] adjPro;//顶点到相邻顶点的 概率
	public double[][] sumPro;//顶点到顶点的概率
	
	AreaGraph() {
		super();
	}
	
	AreaGraph(int size,int areasize) {
		super(size);
		areaVector = new Vector<Area>(areasize);
		sumPro = new double[size][size];
	}
	
	void add(Area Area) {
		areaVector.add(Area);
	}
	
//	初始化顶点到相邻顶点的概率
	void initAdjPro(double [][] adjPro) {
		this.adjPro = adjPro;
	}
	

//	计算栈底顶点沿栈中顶点轨迹到des顶点的概率
	public double eleProMult(int des) {
		float mult = 1;
		int prior;
		int post;
		int peek = stack.peek();
		for(int i=0; i<stack.top; i++) {
			prior = stack.st[i];
			post = stack.st[i+1];
			mult *= adjPro[prior][post];
		}
		mult *= adjPro[peek][des];
		return mult;
	}
	
//	获得相邻顶点
	Vector getAdjVertex(int num) {
		Vertex vertex = vertexs.get(num);
		adjVertex[num] = new Vector();
		for(int i=0; i<vertexs.size(); i++) {
			int area = vertexs.get(i).area;
			if(vertex.area != mainArea.areaNum) {
//				vertex.area != area
				if(adjMat[num][i] == 1  && mainArea.areaNum == area) 
//					&& mainArea.areaNum != area
					adjVertex[num].add(i);
			}
			else {
				if(adjMat[num][i] == 1 && (vertexs.get(i).area) == mainArea.areaNum 
						&& !stack.isContains(i))
					adjVertex[num].add(i);
			}
		}
		return adjVertex[num];
	}
		
	/**找到从sre顶点到des顶点的所有路径，并计算从sre顶点到des顶点概率
	 * @param sre 主区域中边顶点
	 * @param des 相邻区域中边顶点
	 */
	void findPath(int sre, int des) {
		int from = des;
		int to = sre;
		stack.push(from);
		Vector adjNode = getAdjVertex(from);
		for(int i=0; i<adjNode.size(); i++) {
			int temp = ((Integer) adjNode.get(i)).intValue();
			if(temp != to) {
				findPath(to, temp);
				stack.pop();
			}
			else {
//				stack.list();
//				System.out.println(eleProMult(to));
				tempPro += eleProMult(to);
//				System.out.println(tempPro);
			}
		}

		}
	}



public class GFindPath extends AreaGraph {

	GFindPath(int size, int areasize) {
		super(size, areasize);
	}
	
//	打印二维数组
	void print(double[][] matrix) {
		for(int i=0; i<matrix.length; i++) {
			for(int j=0; j<matrix[i].length; j++) {
				System.out.print(matrix[i][j] + "    ");
			}
		System.out.println();
		}
	}


	public static void main(String[] args) {
		int[] area = {0, 1, 2, 3, 4};
		int[] vertex = {9, 3, 4, 3, 2};
		int vSzie = 21;
		GFindPath graph = new GFindPath(vSzie, area.length);
		Vertex ver;
		Area are;
		
		int [][] adjMat =
		   {{0,	1,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	1,	0,	0,	0,	0,	0},
			{1,	0,	1,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0},
			{0,	1,	0,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0},
			{1,	0,	0,	1,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	1,	0,	0,	1,	0,	0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	1,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0},
			{0,	0,	0,	0,	1,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0},
			{0,	0,	0,	0,	0,	1,	0,	1,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	1},
			{0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	1,	0,	0,	1,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0},
			{0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0},
			{0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0},
			{1,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0},
			{0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	1},
			{0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0}};
		
		double [][] adjPro	= 
		   {{0,	0.5,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0,	0.5,	0,	0,	0,	0,	0},
			{0.5,	0,	0.5,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0},
			{0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0.5,	0.5,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0,	0.5,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0.5,	0,	0,	0,	0},
			{0.5,	0,	0,	0.5,	0,	0.5,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0.5,	0,	0,	0.5,	0,	0,	0,	0.5,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0.5,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0.5,	0,	0},
			{0,	0,	0,	0,	0.5,	0,	0.5,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0},
			{0,	0,	0,	0,	0,	0.5,	0,	0.5,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0.5},
			{0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0.5,	0,	0,	0.5,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0},
			{0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0.5,	0,	0,	0,	0,	0,	0},
			{0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0},
			{0.5,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
			{0,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0},
			{0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0.5},
			{0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.5,	0}};
		

		int vertexno = 0, limit = vertex[0];
//		把顶点添加到图中，并且把各顶点添加到各个对应的区域中			
		for(int i=0; i<area.length; i++) {			
			are = new Area(vertex[i], i);		
			for(int j=vertexno; j<limit; j++) {
				ver = new Vertex(j, i);			
				graph.add(ver);
				are.add(ver);
//				System.out.print(ver.value + " " + ver.area + "  ");
			}			
			graph.add(are);			
			vertexno += vertex[i];
			
			if(are.areaNum == 0)
				graph.mainArea = are;
			if(i < 4)
				limit += vertex[i+1];
			
		}
		
		System.out.println();
		
//		初始化graph的相邻矩阵和相邻概率矩阵
		graph.initAdjMat(adjMat);
		graph.initAdjPro(adjPro);
		
		
		
		
		int mAreaSize = graph.mainArea.vertexnum;//主区域顶点个数
		double[][] mAVProSum = new double[mAreaSize][area.length];//主区域各顶点到相邻区域的概率
		Area tempArea;
		
//		计算主区域各个顶点到所有相邻区域各边界顶点的概率	
		for(int i=0; i<mAreaSize; i++) {
			int aStarNum = mAreaSize;
			int border = mAreaSize;
			int j = 0;
//			计算主区域顶点i到所有相邻区域边界顶点的概率
			for(; j<area.length; j++) {
				tempArea =graph.areaVector.get(j);
				
/*				tempArea区域号如果不等于主区域号，计算主区域顶点i到tempArea区域各顶点的概率
				并计算主区域顶点i到区域j的概率*/
				if(tempArea.areaNum != graph.mainArea.areaNum)  {
					border += vertex[j];
					for(int k=aStarNum; k<border; k++) {
						graph.findPath(i, k);
						graph.stack.clean();
						graph.sumPro[i][k] = graph.tempPro;
						mAVProSum[i][j] += graph.sumPro[i][k];
						graph.tempPro = 0;
					}	
					aStarNum += vertex[j];
				}
			}
		}
		
//		graph.print(graph.sumPro);		
		graph.print(mAVProSum);

		double[] areaPro = new double[area.length];//主区域到各相邻区域的概率
		double proSum = 0;
		for(int i=1; i<area.length; i++) {
			for(int j=0; j<mAreaSize; j++) {
				areaPro[i] += mAVProSum[j][i];
			}
			System.out.println(areaPro[i] + " ");
			proSum += areaPro[i];
		}
		
//		System.out.println(proSum);
		
		for(int i=1; i<area.length; i++) {
			areaPro[i] = areaPro[i]/proSum;
			System.out.println(areaPro[i] + " ");
		}
		
		
		
	}
	
}
