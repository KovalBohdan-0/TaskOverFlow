import { ShortTask } from './task/ShortTask';

export interface TaskList {
    id: number;
    title: string;
    tasks: ShortTask[];
    boardId: number;
    position: number;
}
