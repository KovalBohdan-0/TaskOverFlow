import { Task } from './task/Task';

export interface TaskList {
    id: number;
    title: string;
    tasks: Task[];
    boardId: number;
}