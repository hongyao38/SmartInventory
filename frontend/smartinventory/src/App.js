import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';


const Stack = createStackNavigator();

function App() {
  return (
		<NavigationContainer>
			<Stack.Navigator screenOptions={{ headerShown: false }}>
        
			</Stack.Navigator>
		</NavigationContainer>
	);
}

export default App;
