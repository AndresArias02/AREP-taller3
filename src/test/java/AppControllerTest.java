import edu.eci.arep.controller.AppController;
import edu.eci.arep.services.GetProductsService;
import edu.eci.arep.utils.ShoppingListMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static edu.eci.arep.services.GetProductsService.cartService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * AppControllTest class
 */
public class AppControllerTest {

    @InjectMocks
    private AppController appController;

    @Mock
    private ShoppingListMemory shoppingListMemory;

    private GetProductsService getProductsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Configurar el mock de ShoppingListMemory
        when(shoppingListMemory.getAllProducts()).thenReturn(Map.of(
                "Product1", "Description1",
                "Product2", "Description2"
        ));
        // Crear una instancia de GetProductsService con el mock
        getProductsService = new GetProductsService(shoppingListMemory);
    }

    @Test
    void testHelloNameWithDefault() {
        // El nombre está vacío, debería usar el valor por defecto
        String response = appController.helloName(null);
        assertEquals("Hola Luis Daniel Benavides Navarro", response);
    }

    @Test
    void testHelloNameWithCustomName() {
        // Proporcionar un nombre personalizado
        String response = appController.helloName("Juan Pérez");
        assertEquals("Hola Juan Pérez", response);
    }

    @Test
    void testProducts() {
        // Configurar el mock de cartService
        Map<String, String> mockProducts = new HashMap<>();
        mockProducts.put("Product1", "Description1");
        mockProducts.put("Product2", "Description2");
        when(cartService.getAllProducts()).thenReturn(mockProducts);

        // Llamar al método y verificar el resultado
        String result = getProductsService.getAll();
        assertNotNull(result);
    }
}
