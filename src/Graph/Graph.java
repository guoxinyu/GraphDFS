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

	void dsf(int index) {	//��ָ���±�Ľڵ㿪ʼ������ȱ���
		if(vertexs.get(index) == null) return;	//���ͼ��û��ָ���±�ڵ㣬���˳�

		Stack s = new Stack(vertexs.size());	//����ջ���ط��ʹ��Ľڵ���±�
		vertexs.get(index).visit();	//����0�ڵ�
		s.push(index);	//��¼0�ڵ�

		while(!s.isEmpty()) {	//������м�¼�Ľڵ������
			index = findNeighbor(s.peek());	//Ѱ��ջ���ڵ��δ���ʹ����ھ�
			if(index != -1) {	//����ҵ�
				vertexs.get(index).visit();	//���ʸýڵ�
				s.push(index);	//��¼�ýڵ�
			} else s.pop();		//û��δ���ʹ��Ľڵ㣬���ջ
		}	//���ջδ�������������
		clean();	//������з��ʱ���
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