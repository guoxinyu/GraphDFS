package Graph;
import java.util.Vector;

class Stack {
	int[] values;
	int pos = -1;
	Stack(int size) {
		values = new int[size];
	}
	void push(int value) { values[++pos] = value; }
	int pop() { return values[pos--]; }
	int peek() { return values[pos]; }
	boolean isEmpty() { return pos == -1; }
}

class Vertex {
	 int value;
	 int area;
	boolean isVisited;
	Vertex(int value, int area) {
		this.value = value;
		this.area = area;
	}

	void visit() { isVisited = true; print(); }
	void clean() {	isVisited = false; }
	boolean isVisited() { return isVisited; }
	void print() { System.out.println(value); }
}

public class Graph {
	Vector<Vertex> vertexs;
	int[][] adjMat;
	int vertexnum;

	Graph(int size) {
		vertexnum = size;
		vertexs = new Vector<Vertex>(size);
		adjMat = new int[size][size];
	}

	void add(Vertex vertex) {
		vertexs.add(vertex);
	}

	void initMatrix(int [][] matrix) {
		this.adjMat = matrix;
	}

	int findNeighbor(int index) {
		for(int i=0; i<vertexs.size(); i++) {
			if(adjMat[index][i] == 1 && !vertexs.get(i).isVisited()) return i;
		}
		return -1;
	}

	void dsf(int index) {	//从指定下标的节点开始深度优先遍历
		if(vertexs.get(index) == null) return;	//如果图中没有指定下标节点，则退出

		Stack s = new Stack(vertexs.size());	//创建栈记载访问过的节点的下标
		vertexs.get(index).visit();	//访问0节点
		s.push(index);	//记录0节点

		while(!s.isEmpty()) {	//如果还有记录的节点则继续
			index = findNeighbor(s.peek());	//寻找栈顶节点的未访问过的邻居
			if(index != -1) {	//如果找到
				vertexs.get(index).visit();	//访问该节点
				s.push(index);	//记录该节点
			} else s.pop();		//没有未访问过的节点，则出栈
		}	//如果栈未空则代表遍历完成
		clean();	//清除所有访问标致
	}

	void clean() { for(Vertex v: vertexs) if(v != null)v.clean(); }

	public static void main(String[] args) {
		Graph graph = new Graph(21);
		Vertex ver;
		
		int [][] matrix =
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
		
		int[] area = {0, 1, 2, 3, 4};
		int[] vertex = {9, 3, 4, 3, 2};
		int i, j, vertexno = 0, limit = vertex[0];
		
		for(i=0; i<area.length; i++) {
			for(j=vertexno; j<limit; j++) {
				ver = new Vertex(j, area[i]);
				graph.add(ver);
				System.out.print(ver.value + " ");
			}			
			vertexno += vertex[i];
			if(i < 4)
				limit += vertex[i+1];		
		}
		
		System.out.println();
		
		graph.initMatrix(matrix);
		graph.dsf(1);
	}
}