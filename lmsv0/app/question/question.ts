export class Question {
    id: string;
    categoryId: string;
    title: string;
    answers: string[];
    deepDiveQuestions?: Question[]
}