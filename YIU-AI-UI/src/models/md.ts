import { MicroDegreeCategory } from "./enum";
import { SubjectProps } from "./subject";

export interface MD {
  [x: string]: any;
  title: string;
  description: string;
  category: MicroDegreeCategory;
  subjects: SubjectProps[];
  createdAt: string;
  updatedAt: string;
}
