export interface TaskFull {
  id: number;
  title: string;
  done: boolean;
  priority: any;
  description: string;
  deadlineDate: Date;
  taskListId: number;
}
