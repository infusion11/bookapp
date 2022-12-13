export interface Book {
    id: number | null;
    isbn: number;
    title: string;
    author: string;
    about: string;
    image: string | null,
    stores: Array<any>;
}