export class Question {
    id!: number;
    questionText!: string;
    answerText!: string;
    parentQuestionId!: number;
    subQuestions!: Question[];
    showAnswerText!: boolean;
}