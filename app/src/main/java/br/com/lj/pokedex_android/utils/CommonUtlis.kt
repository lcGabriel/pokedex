package br.com.lj.pokedex_android.utils

class CommonUtlis {
    /**
     * Utility class to perform commons methods and logics in the App.
     */
    class CommonUtils {
        companion object {
            fun changeColorTypePoKemon(typePokemon: String):String {
                when(typePokemon){
                    "normal" ->{
                        return "#a4a4a4"
                    }
                    "fighting"->{
                        return "#d56723"
                    }
                    "flying"->{
                        return "#3dc7ef"
                    }
                    "poison"->{
                        return "#b97fc9"
                    }
                    "ground"->{
                        return "#ab9842"
                    }
                    "rock"->{
                        return "#a38c21"
                    }
                    "bug"->{
                        return "#729f3f"
                    }
                    "ghost"->{
                        return "#7b62a3"
                    }
                    "steel"->{
                        return "#9eb7b8"
                    }
                    "fire"->{
                        return "#fd7d24"
                    }
                    "water"->{
                        return "#4592c4"
                    }
                    "grass"->{
                        return "#9bcc50"
                    }
                    "electric"->{
                        return "#eed535"
                    }
                    "psychic"->{
                        return "#f366b9"
                    }
                    "ice"->{
                        return "#50c3e6"
                    }
                    "dragon"->{
                        return "#f06e57"
                    }
                    "dark"->{
                        return "#707070"
                    }
                    "fairy"->{
                        return "#fdb9e9"
                    }
                    "unknown"->{
                        return "#dedede"
                    }
                    "shadow"->{
                        return "#181717"
                    }
                }
                return "#f2f2f2"
            }
        }
    }
}