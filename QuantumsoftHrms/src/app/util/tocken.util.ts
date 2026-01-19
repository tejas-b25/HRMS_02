export class TokenUtil {

  static getToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  static getPayload(): any | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
      console.error('Invalid token', e);
      return null;
    }
  }

  static getUsername(): string | null {
    return this.getPayload()?.sub || null;
  }

  static getRole(): string | null {
    return this.getPayload()?.role || null;
  }

  static isTokenExpired(): boolean {
    const exp = this.getPayload()?.exp;
    if (!exp) return true;

    return Date.now() >= exp * 1000;
  }
}