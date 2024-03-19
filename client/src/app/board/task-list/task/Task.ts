export interface Task {
  id: number;
  title: string;
  description: string;
  priority: string;
  taskListId: number;
  createdAt: string;
  deadline: string;
  done: boolean;
}
