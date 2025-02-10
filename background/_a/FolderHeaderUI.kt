package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.dom.Div


//@Composable
//fun FolderHeaderUI(
//    handleExpand: () -> Unit
//) {
//
//    var isHovered = remember { mutableStateOf(false) }
//
//    Div(
//        attrs = {
//            classes("flex")
//        }
//    ) {
//
//    }
//
//}


//interface FolderHeaderProps {
//    id: number;
//    name: string;
//}
//
//const FolderHeader: React.FC<FolderHeaderProps> = ({ id, name }) => {
//    const [isHovered, setIsHovered] = useState<boolean>(false);
//    const handleExpand = () => {
//        console.log("handle expand from workflow summary header");
//    }
//
//    return (
//    <div className="flex"
//    onMouseEnter={() => setIsHovered(true)}
//    onMouseLeave={() => setIsHovered(false)}
//    >
//
//    <div className="pr-2">
//    <Icon icon={Icons.Folder} className="w-28 h-28 text-white" />
//    </div>
//    <h2 className="flex-grow truncate text-xl font-bold text-white">{name}</h2>
//    {isHovered && (
//        <div className="flex space-x-2">
//        <IconButton icon={Icons.Clone} onClick={() => {}} />
//        <IconButton icon={Icons.Junction} onClick={() => {}} />
//        </div>
//        )}
//    </div>
//    );
//}
//
//export default FolderHeader;