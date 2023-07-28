import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import  { IProductData } from "../types/product-type";

interface IState {
    isOpenLeftBar: boolean,
    products: IProductData[],
    selectedCategoryId: number,
    selectedPage: number
}

const pI:IProductData = {
    id: 1,
    name: '',
    categoryId: 1,
    pictures: [{
        src:'',
        title:''
    }],
    description:''

}

const initialState: IState = {
    isOpenLeftBar: false,
    products: [pI],
    selectedCategoryId: 0,
    selectedPage: 1
}

const sliceProduct = createSlice({
    name: 'product',
    initialState: initialState,
    reducers: {
        setToggleLeftBar(state, action: PayloadAction<boolean>) {
            state.isOpenLeftBar = action.payload
        },
        setProducts(state, action: PayloadAction<IProductData[]>) {
            state.products = action.payload
        },
        setSelectedCategoryId(state, action: PayloadAction<number>) {
            debugger
            if (action.payload > 0) {
                if (action.payload !== state.selectedCategoryId) {
                    state.selectedPage = 1
                }
                state.selectedCategoryId = action.payload
            }
        },
        setPage(state, action: PayloadAction<number>) {
            if(action.payload <= 0) {
                return;
            }
            state.selectedPage = action.payload
        },
        setToggleLeftBarPage(state, action: PayloadAction<boolean>) {
            state.isOpenLeftBar = action.payload
        },
    }
})

export default sliceProduct;
