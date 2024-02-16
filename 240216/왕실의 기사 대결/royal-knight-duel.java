import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static int L, N, Q;
	static int map[][];
	static List<Knight> knight;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	static int res = 0;

	static class Knight {
		int r, c, h, w, k, damage;
		boolean flag, m;

		// 위치, 높이, 너비, 체력
		public Knight(int r, int c, int h, int w, int k) {
			this.r = r;
			this.c = c;
			this.h = h;
			this.w = w;
			this.k = k;
			this.damage = 0;
			this.flag = false;
			this.m = false;
		}

		@Override
		public String toString() {
			return "Knight [r=" + r + ", c=" + c + ", h=" + h + ", w=" + w + ", k=" + k + ", flag=" + flag + "]";
		}

	}

	static boolean wallCheck(int r, int c) {
		if (r < 1 || r > L)
			return true;
		if (c < 1 || c > L)
			return true;
		if (map[r][c] == 2)
			return true;
		return false;
	}

	static boolean moveCheck(int r, int c, int h, int w, int d) {

		if (d == 0) {
			for (int i = 1; i <= N; i++) {
				if (knight.get(i).k <= 0)
					continue;
				for (int j = c; j < c + w; j++) {
					if (wallCheck(r - 1, j))
						return false;
					int kr = knight.get(i).r + knight.get(i).h - 1;
					int kc = knight.get(i).c;
					for (int k = kc; k < kc + knight.get(i).w; k++) {
						if (kr == r - 1 && k == j) {
							if (wallCheck(r - 2, j)) {
								knight.get(i).flag = false;
								return false;
							}
						}
						knight.get(i).flag = true;
					}

				}
			}
		}

		else if (d == 1) {
			for (int i = 1; i <= N; i++) {
				if (knight.get(i).k <= 0)
					continue;
				for (int j = r; j < r + h; j++) {
					if (wallCheck(j, c + 1))
						return false;
					int kr = knight.get(i).r;
					int kc = knight.get(i).c;
					for (int k = kr; k < kr + knight.get(i).h; k++) {
						if (kc == c + 1 && k == j) {
							if (wallCheck(j, c + 2)) {
								knight.get(i).flag = false;
								return false;
							}
							knight.get(i).flag = true;
						}
					}

				}
			}
		}

		else if (d == 2) {
			for (int i = 1; i <= N; i++) {
				if (knight.get(i).k <= 0)
					continue;
				for (int j = c; j < c + w; j++) {
					if (wallCheck(r - 1, j))
						return false;
					int kr = knight.get(i).r;
					int kc = knight.get(i).c;
					for (int k = kc; k < kc + knight.get(i).w; k++) {
						if (kr == r - 1 && k == j) {
							if (wallCheck(r - 2, j)) {
								knight.get(i).flag = false;
								return false;
							}
							knight.get(i).flag = true;
						}
					}

				}
			}
		}

		else {
			for (int i = 1; i <= N; i++) {
				if (knight.get(i).k <= 0)
					continue;
				for (int j = r; j < r + h; j++) {
					if (wallCheck(j, c - 1))
						return false;
					int kr = knight.get(i).r;
					int kc = knight.get(i).c + knight.get(i).w - 1;
					for (int k = kr; k < kr + knight.get(i).h; k++) {
						if (kc == c-1 && k == j) {
							if (wallCheck(j, c - 2)) {
								knight.get(i).flag = false;
								return false;
							}
							knight.get(i).flag = true;
						}
					}

				}
			}
		}

		return true;
	}

	static void hp(int kNum) {
		for (int i = 1; i <= N; i++) {
			if (i == kNum)
				continue;
			if (knight.get(i).k <= 0)
				continue;
			int r = knight.get(i).r;
			int c = knight.get(i).c;
			int h = knight.get(i).h;
			int w = knight.get(i).w;
			
			for (int j = r; j < r + h; j++) {
				for (int k = c; k < c + w; k++) {
					if (map[j][k] == 1 && knight.get(i).m) {
//						res++;
						knight.get(i).damage += 1;
						knight.get(i).k -= 1;
					}
					
				}
			}
			knight.get(i).m = false;
		}
	}

	static void move(int kNum, int d) {
		int r = knight.get(kNum).r;
		int c = knight.get(kNum).c;
		int h = knight.get(kNum).h;
		int w = knight.get(kNum).w;

		if (moveCheck(r, c, h, w, d)) {
			knight.get(kNum).flag = true;
			for (int i = 1; i <= N; i++) {
				if (knight.get(i).flag) {
					knight.get(i).r += dx[d];
					knight.get(i).c += dy[d];
					knight.get(i).flag = false;
					knight.get(i).m = true;
					
				}
			}
			knight.get(kNum).flag = false;
		} else {
			for (int i = 1; i <= N; i++) {
				knight.get(i).flag = false;
			}
		}

		hp(kNum);
//		for(int i=1; i<=N; i++) {
//		System.out.println(kNum);
//		System.out.println(knight.toString());
////		}
//		System.out.println();

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		map = new int[L + 1][L + 1];
		for (int i = 1; i <= L; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= L; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		knight = new ArrayList<>();
		knight.add(null);
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			knight.add(new Knight(r, c, h, w, k));
		}
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int num = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			move(num, d);
		}
		for(int i=1; i<=N; i++) {
			if(knight.get(i).k > 0) res += knight.get(i).damage;
		}
		System.out.println(res);
	}

}