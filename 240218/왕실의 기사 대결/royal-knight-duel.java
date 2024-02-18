import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int L, N, Q; // 체스판 크기, 기사의 수, 명령의 수
	static int[][] map;
	static knightInfo[] knight;
//	int res = 0;
	
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	
	static class knightInfo{
		int r, c, h, w, k, dmg;
		boolean flag;

		public knightInfo(int r, int c, int h, int w, int k) {
			this.r = r;
			this.c = c;
			this.h = h;
			this.w = w;
			this.k = k;
			dmg = 0;
			flag = false;
		}
		
	}
	static boolean rangeCheck(int r, int c, int h, int w) {
		return r >= 1 && r+h-1 <= L && c >= 1 && c+w-1 <= L;
	}
	static boolean moveCheck(int kNum, int d) {
		Queue<Integer> q = new ArrayDeque<>();
		
		q.add(kNum);
		knight[kNum].flag = true;

		while(!q.isEmpty()) {
			int idx = q.poll();
//			System.out.println(idx + " " + knight[idx].r + " " + knight[idx].c);
			int r = knight[idx].r + dx[d];
			int c = knight[idx].c + dy[d];
			int h = knight[idx].h;
			int w = knight[idx].w;
			
			if(!rangeCheck(r, c, h, w)) return false;

			for(int i=r; i<r+h; i++) {
				for(int j=c; j<c+w; j++) {
 					if(map[i][j] == 1) {
						knight[idx].dmg++;
					}
					else if(map[i][j] == 2) {
						return false;
					}
				}
			}
//			knight[idx].flag = true;
//			r -= dx[d];
//			c -= dy[d];
//			
			for(int i=1; i<=N; i++) {
				if(knight[i].k <= 0 || knight[i].flag) continue;

//				if(knight[i].r > r + h-1 || knight[i].r + knight[i].h-1 < r)
//					continue;
//				if(knight[i].c > c + w -1|| knight[i].c + knight[i].w -1< c)
//					continue;
				if(knight[i].r <= r + h-1 && knight[i].r + knight[i].h-1 >= r && knight[i].c <= c + w -1 && knight[i].c + knight[i].w -1 >= c) {
					
					q.add(i);
					knight[i].flag = true;
				}
				
				
			}
		}
		knight[kNum].dmg = 0;
		return true;
	}
	
	static void move(int kNum, int d) {
		if(moveCheck(kNum, d)) {
			for(int i=1; i<=N; i++) {
				if(knight[i].flag) {
					knight[i].r += dx[d];
					knight[i].c += dy[d];
					knight[i].k -= knight[i].dmg;
				}
				knight[i].flag = false;
				knight[i].dmg = 0;
			}
		}
		else {
			for(int i=1; i<=N; i++) {
				knight[i].flag = false;
				knight[i].dmg = 0;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		map = new int[L+1][L+1];
		knight = new knightInfo[N+1];
		int[] fk = new int[N+1];
		for(int i=1; i<=L; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=L; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			knight[i] = new knightInfo(r, c, h, w, k);
			fk[i] = knight[i].k;
		}
		
		for(int i=0; i<Q; i++) {
			st = new StringTokenizer(br.readLine());
			int kNum = Integer.parseInt(st.nextToken()); 
			int d = Integer.parseInt(st.nextToken()); 
			if(knight[kNum].k > 0)
				move(kNum, d);
		}
		int res = 0;
		for(int i=1; i<=N; i++) {
//			System.out.println(knight[i].k);
			if(knight[i].k > 0) res += fk[i] - knight[i].k;
		}
		
		System.out.println(res);
		
	}
}