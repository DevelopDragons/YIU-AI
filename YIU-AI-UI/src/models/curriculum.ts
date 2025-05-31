export interface Curriculum {
    id: number;
    title: string;
    course: string;
    grade: number;
    term: number;
    credit: number;
    description: string;
    classes: string;
    theory: number;
    practice: number;
    code: string;
    createdAt: string;
    updatedAt: string;
}

const curriculumList: Curriculum[] = [
    {
        id: 1,
        title: "string",
        course: "string",
        grade: 1,
        term: 1,
        credit: 1,
        description: "string",
        classes: "string",
        theory: 1,
        practice: 1,
        code: "string",
        createdAt: "2024-08-31T22:02:40.537994",
        updatedAt: "2024-08-31T22:02:40.537994",
    },
];
export default curriculumList;