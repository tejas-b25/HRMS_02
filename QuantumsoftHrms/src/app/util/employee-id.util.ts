export class EmployeeUtil {

  static getEmployeeIdByName(response: any[], name: string): number | null {
    if (!response || !Array.isArray(response)) {
      return null;
    }

    const employee = response.find(emp =>
      emp?.name?.toLowerCase() === name.toLowerCase() ||
      emp?.firstName?.toLowerCase() === name.toLowerCase()
    );

    return employee?.employeeId ?? employee?.id ?? null;
  }
    static getEmployeeId(): number {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('Token not found');
    }

    const payload = JSON.parse(atob(token.split('.')[1]));

    // ✅ adjust key if backend uses different name
    return payload.employeeId || payload.id;
  }
}